package com.light.eventApp.util;

import com.light.eventApp.AuthorizedUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

@UtilityClass
public class SecurityUtil {
    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static Long authUserId() {
        return get().getUserTo().id();
    }
}

