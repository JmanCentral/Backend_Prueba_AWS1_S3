package com.WishApp.WishApp.excepciones.User;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
