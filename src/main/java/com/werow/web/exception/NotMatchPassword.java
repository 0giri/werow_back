package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class NotMatchPassword extends CustomException {
    public NotMatchPassword(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
