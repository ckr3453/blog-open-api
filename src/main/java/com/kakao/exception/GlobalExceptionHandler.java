package com.kakao.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : BlogApiRequest
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */

@Slf4j
@RestControllerAdvice(basePackages = "com.kakao")
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> bindExceptionHandler(BindException e) {
        String message = "\n=*=*=*=     BindException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_ARGUMENT, e.getBindingResult().getFieldErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        String message = "\n=*=*=*=     MethodArgumentTypeMismatchException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.ARGUMENT_TYPE_MISMATCH), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = "\n=*=*=*=     MethodArgumentNotValidException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.ARGUMENT_NOT_VALID, e.getBindingResult().getFieldErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        String message = "\n=*=*=*=     NoHandlerFoundException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.NOT_FOUND), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        String message = "\n=*=*=*=     HttpRequestMethodNotSupportedException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception e) {
        String message = "\n=*=*=*=    Exception     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}