package com.kakao.domain.search.controller;

import com.kakao.domain.search.dto.BlogResponse;
import com.kakao.domain.search.dto.KeywordResponse;
import com.kakao.domain.search.repository.KeywordRepository;
import com.kakao.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : com.kakao.domain.search.controller
 * fileName    : SearchController
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */

@Slf4j
@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final KeywordRepository keywordRepository;

    // 파라미터 검증로직 필요
    @GetMapping("/blog")
    public ResponseEntity<BlogResponse> getBlog(@RequestParam String query,
                                                @RequestParam(required = false) String sort, // accuracy / recency / "" 만 가능
                                                @RequestParam(required = false) Integer page, // 1~50만 가능 (기본 1)
                                                @RequestParam(required = false) Integer size){ // 1~50만 가능 (기본 10)
        searchService.saveKeyword(query);
        BlogResponse blogResponse = searchService.getBlogFromApi(query, sort, page, size);
        return new ResponseEntity<>(blogResponse, HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<KeywordResponse>> getKeyword(){
        return new ResponseEntity<>(keywordRepository.findTop10ByOrderByHitsDesc(), HttpStatus.OK);
    }
}
