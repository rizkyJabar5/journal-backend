package com.journal.florist.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        code = HttpStatus.NOT_FOUND,
        reason = "Your request is not found.")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
