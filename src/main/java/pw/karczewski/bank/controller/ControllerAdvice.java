package pw.karczewski.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import pw.karczewski.bank.exception.AccountNotFoundException;
import pw.karczewski.bank.exception.ValidationException;

@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger log = LogManager.getLogger(ControllerAdvice.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
