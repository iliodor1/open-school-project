package ru.eldar.exception;

public class ContextException extends RuntimeException {
    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextException(String message) {
        super(message);
    }
}