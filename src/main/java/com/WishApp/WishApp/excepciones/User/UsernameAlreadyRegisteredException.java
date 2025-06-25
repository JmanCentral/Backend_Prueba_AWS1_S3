package com.WishApp.WishApp.excepciones.User;

public class UsernameAlreadyRegisteredException extends RuntimeException {
    public UsernameAlreadyRegisteredException(String message) {
        super(message);
    }
}
