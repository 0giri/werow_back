package com.werow.web.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public abstract class CustomException extends RuntimeException {

    private int status;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus.value();
    }
}
