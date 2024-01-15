package com.magasinEnLigne.ecommerce.exception;

public class StateAlreadyExistsException extends RuntimeException {

    public StateAlreadyExistsException(String message) {
        super(message);
    }

    public StateAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
