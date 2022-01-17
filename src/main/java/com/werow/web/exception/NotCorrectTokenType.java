package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class NotCorrectTokenType extends CustomException {

    public NotCorrectTokenType(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
