package com.kakao.domain.search.service;

import com.kakao.domain.search.dto.BlogApiRequest;
import com.kakao.domain.search.dto.BlogApiResponse;
import com.kakao.domain.search.dto.KeywordApiResponse;

/**
 * packageName : com.kakao.domain.search.service.impl
 * fileName    : SearchService
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
public interface SearchService {
    BlogApiResponse getBlogFromApi(BlogApiRequest blogApiRequest);
    void saveKeyword(String keyword);
    KeywordApiResponse getKeyword();
}
