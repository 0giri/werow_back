package com.werow.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotJoinedUser extends RuntimeException{
    public NotJoinedUser(String message) {
        super(message);
    }
}
