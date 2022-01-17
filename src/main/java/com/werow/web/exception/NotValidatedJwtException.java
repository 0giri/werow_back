package com.werow.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotValidatedJwtException extends CustomException {

    public NotValidatedJwtException(String message) {
        super(message,HttpStatus.FORBIDDEN);
    }
}
