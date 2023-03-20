package com.kakao.domain.search.service.impl;

import com.kakao.domain.search.dto.*;
import com.kakao.domain.search.entity.Keyword;
import com.kakao.domain.search.repository.KeywordRepository;
import com.kakao.domain.search.resttemplates.KakaoBlogApi;
import com.kakao.domain.search.resttemplates.NaverBlogApi;
import com.kakao.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

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
    public BlogApiResponse getBlogFromApi(BlogApiRequest blogApiRequest) {
        ResponseEntity<KakaoBlogApiResponse> kakaoResponse = kakaoBlogApi.get(blogApiRequest);
        if(kakaoResponse.getStatusCode() == HttpStatus.OK){
            return getBlogResponse(kakaoResponse.getBody(), blogApiRequest.getPage(), blogApiRequest.getSize());
        } else {
            ResponseEntity<NaverBlogApiResponse> naverResponse = naverBlogApi.get(blogApiRequest);
            if(naverResponse.getStatusCode() != HttpStatus.OK) System.out.println("error caused"); // exception 처리
            return getBlogResponse(naverResponse.getBody());
        }
    }

    private BlogApiResponse getBlogResponse(KakaoBlogApiResponse kakaoBlogApiResponse, Integer page, Integer size){
        return BlogApiResponse.builder()
            .page(page)
            .size(size)
            .total(kakaoBlogApiResponse.getMeta().getTotal_count())
            .blogs(kakaoBlogApiResponse.getDocuments().stream().map(document -> BlogApiResponse.Blog.builder()
                .title(document.getTitle())
                .url(document.getUrl())
                .contents(document.getContents())
                .blogName(document.getBlogname())
                .thumbnail(document.getThumbnail())
                .postDate(document.getDatetime())
                .build())
                .collect(Collectors.toList()))
            .build();
    }

    private BlogApiResponse getBlogResponse(NaverBlogApiResponse naverBlogApiResponse){
        return BlogApiResponse.builder()
            .page(naverBlogApiResponse.getStart())
            .size(naverBlogApiResponse.getDisplay())
            .total(naverBlogApiResponse.getTotal())
            .blogs(naverBlogApiResponse.getItems().stream().map(item -> BlogApiResponse.Blog.builder()
                .title(item.getTitle())
                .url(item.getLink())
                .contents(item.getDescription())
                .blogName(item.getBloggername())
                .postDate(item.getPostdate())
                .build())
                .collect(Collectors.toList()))
            .build();
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

    @Override
    public KeywordApiResponse getKeyword() {
        return KeywordApiResponse.builder()
            .keywords(keywordRepository.findTop10ByOrderByHitsDesc())
            .build();
    }
}
