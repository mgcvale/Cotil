package com.github.mgcvale.projetojava.exception;

public class InvalidColorException extends IllegalArgumentException {
    public InvalidColorException(String message) {
        super(message);
    }

    public InvalidColorException(String message, Throwable cause) {
        super(message, cause);
    }
}
