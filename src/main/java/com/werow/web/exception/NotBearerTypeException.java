package com.werow.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotBearerTypeException extends CustomException {

    public NotBearerTypeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
