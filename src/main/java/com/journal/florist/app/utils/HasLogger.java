/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HasLogger {

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}

