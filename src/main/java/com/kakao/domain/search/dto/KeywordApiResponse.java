package com.kakao.domain.search.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : KeywordApiResponse
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Getter
@Builder
public class KeywordApiResponse {

    private List<KeywordProperty> keywords;
}
