package com.seven.test.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    COMPANY_OWNER,
    COMPANY_EMPLOYER;

    @Override
    public String getAuthority() {
        return name();
    }
}