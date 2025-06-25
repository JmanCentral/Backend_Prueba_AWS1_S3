package com.WishApp.WishApp.excepciones.User;

public class RolNotFoundException extends RuntimeException {
    public RolNotFoundException(String message) {
        super(message);
    }
}
