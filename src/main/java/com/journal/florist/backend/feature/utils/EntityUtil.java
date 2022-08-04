/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.utils;

public final class EntityUtil {

    public static String getName(Class<? extends BaseEntity> type){
        return type.getSimpleName();
    }
}
