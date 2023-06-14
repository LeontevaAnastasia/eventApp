package com.light.eventApp.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    PRINCIPAL;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
