package com.kakao.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception e) {
		String message = "\n=*=*=*=    Exception     =*=*=*=\n";
		log.error(message + e.getMessage(), e);
		return new ResponseEntity<>(ErrorResponse.builder()
            .errorMessage("INTERNAL_SERVER_ERROR")
            .description("서버 오류가 발생하였습니다. 관리자에게 문의하세요.")
            .build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        String message = "\n=*=*=*=     MethodArgumentTypeMismatchException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.builder()
            .errorMessage("ILLEGAL_ARGUMENT")
            .description("매개변수의 타입이 올바르지 않습니다. (" + e.getCause() + ")")
            .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = "\n=*=*=*=     MethodArgumentNotValidException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.builder()
            .errorMessage("ILLEGAL_ARGUMENT")
            .description("매개변수의 검증이 실패하였습니다. (" + e.getCause() + ")")
            .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> NoHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        String message = "\n=*=*=*=     NoHandlerFoundException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.builder()
            .errorMessage("URL_NOT_FOUND")
            .description("존재하지 않는 url 입니다.")
            .build(),
            HttpStatus.NOT_FOUND);
    }
}