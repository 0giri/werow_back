package com.werow.web.exception;

public class FailedLoginException extends RuntimeException {
    public FailedLoginException(String message) {
        super(message);
    }
}
