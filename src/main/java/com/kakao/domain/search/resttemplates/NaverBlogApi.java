package com.kakao.domain.search.resttemplates;

import com.kakao.domain.search.dto.BlogApiRequest;
import com.kakao.domain.search.dto.NaverBlogApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * packageName : com.kakao.domain.search.resttemplates
 * fileName    : NaverBlogApi
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Component
@RequiredArgsConstructor
public class NaverBlogApi implements BlogApi {
    private final RestTemplate restTemplate;

    @Value("${naver.blog-search-uri}")
    private String URI;

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.client-secret}")
    private String CLIENT_SECRET;

    @Override
    public ResponseEntity<NaverBlogApiResponse> get(BlogApiRequest blogApiRequest){
        return restTemplate.exchange(
            createURI(blogApiRequest),
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            NaverBlogApiResponse.class
        );
    }

    @Override
    public String createURI(BlogApiRequest blogApiRequest) {
        return UriComponentsBuilder.fromHttpUrl(URI)
            .queryParam("query", blogApiRequest.getQuery())
            .queryParam("display", blogApiRequest.getSize())
            .queryParam("start", blogApiRequest.getPage())
            .queryParam("sort", convertSortStr(blogApiRequest.getSort()))
            .encode()
            .toUriString();
    }

    @Override
    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", CLIENT_ID);
        headers.add("X-Naver-Client-Secret", CLIENT_SECRET);
        return headers;
    }

    private String convertSortStr(String sort){
        return "recency".equals(sort) ? "date" : "sim";
    }
}
