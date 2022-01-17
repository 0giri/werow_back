package com.werow.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotEnoughAuthorityException extends CustomException {

    public NotEnoughAuthorityException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
