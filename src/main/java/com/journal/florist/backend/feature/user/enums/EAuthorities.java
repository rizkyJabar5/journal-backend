/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.enums;

import java.util.Arrays;

public enum EAuthorities {

    WRITE,
    READ,
    UPDATE,
    DELETE;

    public static String getAllAuthorities() {
        return Arrays.toString(EAuthorities.class.getEnumConstants());
    }


}
