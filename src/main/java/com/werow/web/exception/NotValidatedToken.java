package com.werow.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotValidatedToken extends RuntimeException {

    public NotValidatedToken(String message) {
        super(message);
    }
}
