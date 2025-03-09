package com.smarbl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Global exception handler for handling various exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException.
     *
     * @param ex the exception
     * @return a response entity with the error message and NOT_FOUND status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors.
     *
     * @param ex the validation exception
     * @return a response entity with the error message and BAD_REQUEST status
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(javax.validation.ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex the exception
     * @return a response entity with a generic error message and INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
