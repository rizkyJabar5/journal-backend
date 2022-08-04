package com.journal.florist.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserFriendlyDataException extends DataIntegrityViolationException {
    public UserFriendlyDataException(String message) {
        super(message);
    }
}
