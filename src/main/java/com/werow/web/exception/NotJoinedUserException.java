package com.werow.web.exception;

import org.springframework.http.HttpStatus;

public class NotJoinedUserException extends CustomException {

    public NotJoinedUserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
