package com.kakao.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : KeywordResponse
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Getter
@Setter
@AllArgsConstructor
public class KeywordResponse {
    private String keyword;
    private Long hits;
}
