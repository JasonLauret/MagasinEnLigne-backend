package com.luv2code.ecommerce.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}