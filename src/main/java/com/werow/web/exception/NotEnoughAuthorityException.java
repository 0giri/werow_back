package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughAuthorityException extends CustomException {

    public NotEnoughAuthorityException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
