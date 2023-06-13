package com.light.eventApp.service;

import com.light.eventApp.model.Role;
import com.light.eventApp.model.User;
import com.light.eventApp.repository.UserRepository;
import com.light.eventApp.to.UserTo;
import com.light.eventApp.util.exception.IncorrectUpdateException;
import com.light.eventApp.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.light.eventApp.util.UserUtil.prepareToSave;
import static com.light.eventApp.util.UserUtil.updateFromTo;
import static com.light.eventApp.util.ValidationUtil.checkNotFound;
import static com.light.eventApp.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User create(User user) {
        return userRepository.save(user);
    }

    public User get(Long id) {
        return checkNotFoundWithId(userRepository.getUserById(id), id);
    }

    public Optional<User> findByEmail(String email){
        return checkNotFound(userRepository.findByEmail(email),"email=" + email);
    }

    public void delete(Long id) {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        prepareAndSave(updateFromTo(user, userTo));

    }

    public void isEnable(Long id, boolean enabled) {
        User user = userRepository.getUserById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User with id " + id + " doesn't exists.");
        }
        user.setEnabled(enabled);
    }

    @Transactional
    public void setAdminRole(Long id) {
        User user = userRepository.getUserById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User with id " + id + " doesn't exists.");
        } else user.setRoles(Set.of(Role.ADMIN));
    }

    @Transactional
    public void removeAdminRole(Long id) {
        User user = userRepository.getUserById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User with id " + id + " doesn't exists.");
        }
        if (!(user.getRoles().toString().contains("ADMIN"))) {
            throw new IncorrectUpdateException();
        }

        Set<Role> roles = user.getRoles();
        roles.remove(Role.ADMIN);
        roles.add(Role.USER);
        user.setRoles(roles);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user));
    }


}

