package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class BadValueException extends CustomException {
    public BadValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
