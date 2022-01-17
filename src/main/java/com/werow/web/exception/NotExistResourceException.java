package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class NotExistResourceException extends CustomException {

    public NotExistResourceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
