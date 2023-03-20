package com.kakao.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.kakao.exception
 * fileName    : ErrorResponse
 * author      : ckr
 * date        : 2023/03/21
 * description :
 */
@Getter
@Setter
@Builder
public class ErrorResponse {

    private String errorMessage;
    private String description;
}
