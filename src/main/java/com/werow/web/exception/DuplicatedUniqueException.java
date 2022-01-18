package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedUniqueException extends CustomException {
    public DuplicatedUniqueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
