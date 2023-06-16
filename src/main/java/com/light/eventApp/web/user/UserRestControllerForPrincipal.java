package com.light.eventApp.web.user;

import com.light.eventApp.model.User;
import com.light.eventApp.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/principal/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestControllerForPrincipal {

    private final UserService userService;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public List<User> getAll() {

        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public User get(@PathVariable Long id) {
        log.info("Get user  by id {}.", id);
        return userService.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Delete user  by id {}.", id);
        userService.delete(id);
    }

    @GetMapping("/by-email")
    public User findByEmail(@RequestParam String email){
        log.info("GET user  by email {}.", email);
        return userService.findByEmail(email).orElse(null);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void isEnable(@PathVariable Long id, @RequestParam boolean enabled) {
        log.info("enable/disable user  by id {}.", id);
        userService.isEnable(id, enabled);
    }


    @PatchMapping("/{id}/set-role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setRole(@PathVariable Long id) {
        log.info("set admin role for user by id {}.", id);
        userService.setAdminRole(id);
    }

    @PatchMapping("/{id}/remove-role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRole(@PathVariable Long id) {
        log.info("remove admin role for user by id {}.", id);
        userService.removeAdminRole(id);
    }
}