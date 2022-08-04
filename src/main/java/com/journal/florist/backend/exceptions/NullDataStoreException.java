package com.journal.florist.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.INTERNAL_SERVER_ERROR,
        code = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "Your request is rejected.")
public class NullDataStoreException extends NullPointerException {
    public NullDataStoreException(String s) {
        super(s);
    }
}
