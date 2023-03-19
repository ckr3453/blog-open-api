package com.kakao.domain.search.resttemplates;

import com.kakao.domain.search.dto.BlogResponse;
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
    private final String URI;

    @Value("${naver.client-id}") // 암호화?
    private final String CLIENT_ID;

    @Value("${naver.client-secret}") // 암호화?
    private final String CLIENT_SECRET;

    public ResponseEntity<BlogResponse> get(final String query, final Integer display, final Integer start, final String sort){
        // TODO: response 형태 통일해야함.
        return restTemplate.exchange(
            createURI(query, display, start, convertSortStr(sort)),
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            BlogResponse.class
        );
    }

    private String convertSortStr(String kakaoSort){
        return "recency".equals(kakaoSort) ? "date" : "sim";
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
