package com.journal.florist.app.common.handlers;

import com.journal.florist.app.common.utils.DateConverter;
import com.journal.florist.backend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseStatusExceptionHandler {

    @ExceptionHandler(IllegalException.class)
    public ResponseEntity<Object> illegalActionDataHandler(IllegalException illegalException,
                                                           HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", illegalException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.CONFLICT.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NullDataStoreException.class)
    public ResponseEntity<Object> nullHandler(NullDataStoreException nullPointerException,
                                              HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", nullPointerException.getCause());
        response.put("status", OperationStatus.ERROR);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundHandler(UsernameNotFoundException usernameNotFoundException,
                                                          HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", usernameNotFoundException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.NOT_FOUND.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundHandler(NotFoundException notFoundException,
                                                  HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", notFoundException.getMessage());
        response.put("status", OperationStatus.NOT_FOUND);
        response.put("code", HttpStatus.NOT_FOUND.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataViolationException.class)
    public ResponseEntity<Object> nonAccessibleHandler(DataViolationException violationException,
                                                       HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", violationException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.NOT_ACCEPTABLE.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(AppBaseException.class)
    public ResponseEntity<Object> mvcRequestHandler(AppBaseException baseException,
                                                    HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", baseException.getMessage());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException constraintException,
                                                               HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", constraintException.getCause());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {

        String date = DateConverter.formatDateTime().format(LocalDateTime.now());
        Map<String, Object> errors = new HashMap<>();
        Map<String, String> error = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((objectError) -> {
                    String field = ((FieldError) objectError).getField();
                    String defaultMessage = objectError.getDefaultMessage();
                    error.put(field, defaultMessage);
                });

        errors.put("timestamp", date);
        errors.put("errors", error);
        errors.put("status", OperationStatus.FAILURE);
        errors.put("code", HttpStatus.BAD_REQUEST.value());
        errors.put("path", request.getServletPath());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
