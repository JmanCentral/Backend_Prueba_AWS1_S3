package com.WishApp.WishApp.excepciones.Category;

public class CategoryNameAlreadyRegisteredException extends RuntimeException {
    public CategoryNameAlreadyRegisteredException(String message) {
        super(message);
    }
}
