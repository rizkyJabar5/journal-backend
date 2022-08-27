package com.journal.florist.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        code = HttpStatus.NOT_FOUND,
        reason = "File not found")
public class StorageException extends FileNotFoundException {

    public StorageException(String message){
        super(message);
    }

}
