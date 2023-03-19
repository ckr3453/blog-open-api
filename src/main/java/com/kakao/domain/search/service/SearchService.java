package com.kakao.domain.search.service;

import com.kakao.domain.search.dto.BlogResponse;

/**
 * packageName : com.kakao.domain.search.service.impl
 * fileName    : SearchService
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
public interface SearchService {
    BlogResponse getBlogFromApi(String query, String sort, Integer page, Integer size);
    void saveKeyword(String keyword);
}
