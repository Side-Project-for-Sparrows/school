package com.sparrows.school.school.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static com.sparrows.school.school.exception.SchoolErrorCode.INVALID_REQUEST;
import static com.sparrows.school.school.exception.SchoolErrorCode.INTERNAL_SERVER;

@Slf4j
@RestControllerAdvice("com.sparrows.school.school")
public class SchoolGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SchoolErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        final SchoolErrorResponse errorResponse = SchoolErrorResponse.of(e);
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<SchoolErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        final SchoolErrorResponse errorResponse = SchoolErrorResponse.of(INTERNAL_SERVER);
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER.getStatus());
    }


    @ExceptionHandler({
            MissingServletRequestPartException.class,
            MissingRequestValueException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<SchoolErrorResponse> handleRequestException(Exception e) {
        log.error(e.getMessage(), e);
        final SchoolErrorResponse errorResponse = SchoolErrorResponse.of(INVALID_REQUEST);
        return new ResponseEntity<>(errorResponse, INVALID_REQUEST.getStatus());
    }

}

