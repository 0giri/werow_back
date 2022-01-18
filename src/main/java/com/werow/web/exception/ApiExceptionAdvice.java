package com.werow.web.exception;

import com.werow.web.commons.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionAdvice {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        loggingError(e);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ExceptionDto(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                        "데이터 형식에 application/json을 사용하세요",
                        request.getRequestURI()));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionDto> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        loggingError(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(),
                        "",
                        request.getRequestURI()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> customExceptionHandler(CustomException e) {
        loggingError(e);
        return ResponseEntity.status(e.getStatus())
                .body(new ExceptionDto(e.getStatus(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    private void loggingError(Exception e) {
        log.warn("[IP] {} , [URI] {} , [Error] {}",
                httpUtils.getClientIP(request), request.getRequestURI(), e.getMessage());
    }
}
