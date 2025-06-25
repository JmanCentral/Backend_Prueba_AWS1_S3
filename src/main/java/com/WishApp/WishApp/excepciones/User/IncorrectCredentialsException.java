package com.WishApp.WishApp.excepciones.User;

public class IncorrectCredentialsException extends RuntimeException {
    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
