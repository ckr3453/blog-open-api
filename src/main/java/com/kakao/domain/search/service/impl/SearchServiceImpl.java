package com.kakao.domain.search.service.impl;

import com.kakao.domain.search.dto.BlogResponse;
import com.kakao.domain.search.dto.KeywordResponse;
import com.kakao.domain.search.entity.Keyword;
import com.kakao.domain.search.repository.KeywordRepository;
import com.kakao.domain.search.resttemplates.KakaoBlogApi;
import com.kakao.domain.search.resttemplates.NaverBlogApi;
import com.kakao.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.kakao.domain.search.service
 * fileName    : SearchService
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final KeywordRepository keywordRepository;
    private final KakaoBlogApi kakaoBlogApi;
    private final NaverBlogApi naverBlogApi;

    @Override
    public BlogResponse getBlogFromApi(String query, String sort, Integer page, Integer size) {
        ResponseEntity<BlogResponse> kakaoResponse = kakaoBlogApi.get(query, sort, page, size);

        if(kakaoResponse.getStatusCodeValue() == 200){
            return kakaoResponse.getBody();
        } else {
            ResponseEntity<BlogResponse> naverResponse = naverBlogApi.get(query, size, page, sort);
            if(naverResponse.getStatusCodeValue() != 200) System.out.println("error caused"); // exception 처리
            return naverResponse.getBody();
        }
    }

    @Override
    public synchronized void saveKeyword(final String query) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByKeyword(query);
        if(optionalKeyword.isEmpty()){
            // 키워드가 없는경우 키워드 row 추가
            keywordRepository.saveAndFlush(Keyword.builder()
                .keyword(query)
                .hits(1L)
                .build());
        } else {
            // 키워드가 있는경우 키워드 hit 추가
            Keyword keyword = optionalKeyword.get();
            keyword.increaseHits();
            keywordRepository.saveAndFlush(keyword);
        }
    }
}
