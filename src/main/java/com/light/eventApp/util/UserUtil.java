package com.light.eventApp.util;

import com.light.eventApp.model.Role;
import com.light.eventApp.model.User;
import com.light.eventApp.to.UserTo;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@UtilityClass
public class UserUtil {

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setAge(userTo.getAge());
        return user;
    }

    public static User createNewFromTo(UserTo userTo) {
        Set<Role> roleSet = Set.of(Role.USER);
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), userTo.getAge(), LocalDate.now(), true, roleSet);
    }

    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail());
        return user;
    }
}
