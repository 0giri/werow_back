package com.werow.web.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class ExceptionDto {
    private final LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    private final int status;
    private final String error;
    private final String path;

    public ExceptionDto(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
