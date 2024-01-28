package pw.karczewski.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import pw.karczewski.bank.exception.AccountNotFoundException;
import pw.karczewski.bank.exception.ValidationException;
import pw.karczewski.bank.model.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger log = LogManager.getLogger(ControllerAdvice.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        var res = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        var res = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        var res = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
