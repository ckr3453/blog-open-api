package com.kakao.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : KeywordProperty
 * author      : ckr
 * date        : 2023/03/21
 * description :
 */
@Getter
@AllArgsConstructor
public class KeywordProperty {
    private String keyword;
    private Long hits;
}
