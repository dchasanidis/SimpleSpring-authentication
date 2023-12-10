package com.dchasanidis.simplespringauthentication.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER(1),
    ADMIN(2),
    ;

    private final int ordinalValue;

    Authority(int ordinalValue) {
        this.ordinalValue = ordinalValue;
    }

    public int getOrdinalValue() {
        return ordinalValue;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
