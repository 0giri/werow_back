package com.werow.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotHaveJwtException extends CustomException {

    public NotHaveJwtException(String message) {
        super(message,HttpStatus.FORBIDDEN);
    }
}
