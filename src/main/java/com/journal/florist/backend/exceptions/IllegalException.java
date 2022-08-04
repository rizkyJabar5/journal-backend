package com.journal.florist.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        code = HttpStatus.BAD_REQUEST,
        reason = "Your request is rejected.")
public class IllegalException extends IllegalArgumentException {

    public IllegalException(String msg) {
        super(msg);
    }
}
