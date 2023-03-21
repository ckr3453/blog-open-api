package com.kakao.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.kakao.exception
 * fileName    : ErrorCode
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_ARGUMENT("매개변수가 유효하지 않습니다."),
    ARGUMENT_TYPE_MISMATCH( "매개변수의 타입이 올바르지 않습니다."),
    ARGUMENT_NOT_VALID("매개변수가 유효하지 않습니다."),
    NOT_FOUND("존재하지 않는 api 입니다."),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다. 관리자에게 문의해주세요."),
    METHOD_NOT_ALLOWED("지원하지 않는 http method 입니다.");

    private final String msg;
}
