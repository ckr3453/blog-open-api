package com.kakao.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName : com.kakao.exception
 * fileName    : ErrorResponse
 * author      : ckr
 * date        : 2023/03/21
 * description :
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String errorMessage;
    private String description;

    private ErrorResponse(final ErrorCode code) {
        this.errorMessage = code.name();
        this.description = code.getMsg();
    }

    private ErrorResponse(final ErrorCode code, final String msg) {
        this.errorMessage = code.name();
        this.description = code.getMsg() + " (" + msg + ")";
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String msg) {
        return new ErrorResponse(code, msg);
    }
}
