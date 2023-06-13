package com.light.eventApp.util;

import com.light.eventApp.model.User;
import com.light.eventApp.to.UserTo;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

@UtilityClass
public class UserUtil {

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setAge(userTo.getAge());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail());
        return user;
    }
}
