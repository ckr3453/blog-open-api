package com.kakao.domain.search.resttemplates;

import com.kakao.domain.search.dto.KakaoBlogApiResponse;
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
public class NaverBlogApi {
    private final RestTemplate restTemplate;

    @Value("${naver.blog-search-uri}")
    private String URI;

    @Value("${naver.client-id}") // 암호화?
    private String CLIENT_ID;

    @Value("${naver.client-secret}") // 암호화?
    private String CLIENT_SECRET;

    public ResponseEntity<NaverBlogApiResponse> get(final String query, final Integer display, final Integer start, final String sort){
        return restTemplate.exchange(
            createURI(query, display, start, convertSortStr(sort)),
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            NaverBlogApiResponse.class
        );
    }

    private String convertSortStr(String sort){
        return "recency".equals(sort) ? "date" : "sim";
    }

    private String createURI(final String query, final Integer display, final Integer start, final String sort) {
        return UriComponentsBuilder.fromHttpUrl(URI)
            .queryParam("query", query)
            .queryParam("display", display)
            .queryParam("start", start)
            .queryParam("sort", sort)
            .encode()
            .toUriString();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", CLIENT_ID);
        headers.add("X-Naver-Client-Secret", CLIENT_SECRET);
        return headers;
    }
}
