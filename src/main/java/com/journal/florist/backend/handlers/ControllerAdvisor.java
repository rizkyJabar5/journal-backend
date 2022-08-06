package com.journal.florist.backend.handlers;

import com.journal.florist.app.utils.DateConverter;
import com.journal.florist.backend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseStatusExceptionHandler {

    @ExceptionHandler(IllegalException.class)
    public ResponseEntity<Object> illegalActionDataHandler(IllegalException illegalException,
                                                           HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String date = DateConverter.formatDate().format(LocalDateTime.now());
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
        String date = DateConverter.formatDate().format(LocalDateTime.now());
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
        String date = DateConverter.formatDate().format(LocalDateTime.now());
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
        String date = DateConverter.formatDate().format(LocalDateTime.now());
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
        String date = DateConverter.formatDate().format(LocalDateTime.now());
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
        String date = DateConverter.formatDate().format(LocalDateTime.now());
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
        String date = DateConverter.formatDate().format(LocalDateTime.now());
        response.put("timestamp", date);
        response.put("message", constraintException.getCause());
        response.put("status", OperationStatus.FAILURE);
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("path", request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
