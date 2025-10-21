package com.CrudPark.app.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
