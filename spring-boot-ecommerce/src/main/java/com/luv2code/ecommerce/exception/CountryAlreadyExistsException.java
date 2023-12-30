package com.luv2code.ecommerce.exception;

public class CountryAlreadyExistsException extends RuntimeException {
    public CountryAlreadyExistsException(String message) {
        super(message);
    }

    public CountryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}