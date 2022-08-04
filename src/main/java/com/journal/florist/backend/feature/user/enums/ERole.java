/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.enums;

public enum ERole{
    ROLE_SUPERADMIN("superadmin"),
    ROLE_OWNER("owner"),
    ROLE_CASHIER("cashier"),
    ROLE_USER("user");

    private final String roleName;

    ERole(String s) {
        roleName = s;
    }

    public boolean equalsname(String otherName){
        return roleName.equalsIgnoreCase(otherName);
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}
