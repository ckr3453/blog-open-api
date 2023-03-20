package com.kakao.domain.search.controller;

import com.kakao.domain.search.dto.BlogApiRequest;
import com.kakao.domain.search.dto.BlogApiResponse;
import com.kakao.domain.search.dto.KeywordApiResponse;
import com.kakao.domain.search.repository.KeywordRepository;
import com.kakao.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * packageName : com.kakao.domain.search.controller
 * fileName    : SearchController
 * author      : ckr
 * date        : 2023/03/19
 * description : 검색 컨트롤러
 */

@Slf4j
@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final KeywordRepository keywordRepository;

    @GetMapping("/blog")
    public ResponseEntity<BlogApiResponse> getBlog(@Valid BlogApiRequest blogApiRequest){
        searchService.saveKeyword(blogApiRequest.getQuery());
        return new ResponseEntity<>(searchService.getBlogFromApi(blogApiRequest), HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<KeywordApiResponse> getKeyword(){
        return new ResponseEntity<>(searchService.getKeyword(), HttpStatus.OK);
    }
}
