package com.WishApp.WishApp.controllers;

import com.WishApp.WishApp.excepciones.Category.CategoryNameAlreadyRegisteredException;
import com.WishApp.WishApp.excepciones.User.EmailAlreadyRegisteredException;
import com.WishApp.WishApp.excepciones.User.IncorrectCredentialsException;
import com.WishApp.WishApp.excepciones.User.RolNotFoundException;
import com.WishApp.WishApp.excepciones.User.UsernameAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    public ResponseEntity<String> handleUsername(UsernameAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<String> handleEmail(EmailAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<String> handleCredentials(IncorrectCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(RolNotFoundException.class)
    public ResponseEntity<String> handleRol(RolNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryNameAlreadyRegisteredException.class)
    public ResponseEntity<String> handleCategory(CategoryNameAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
