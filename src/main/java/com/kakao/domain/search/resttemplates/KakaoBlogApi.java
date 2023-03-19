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
 * fileName    : KakaoBlogApi
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */

@Component
@RequiredArgsConstructor
public class KakaoBlogApi {
    private final RestTemplate restTemplate;

    @Value("${kakao.blog-search-uri}")
    private final String URI;

    @Value("${kakao.api-key}") // 암호화?
    private final String API_KEY;

    public ResponseEntity<BlogResponse> get(final String query, final String sort, final Integer page, final Integer size){
        // TODO: response 형태 통일해야함.
        return restTemplate.exchange(
            createURI(query, sort, page, size),
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            BlogResponse.class
        );
    }

    private String createURI(final String query, final String sort, final Integer page, final Integer size) {
        return UriComponentsBuilder.fromHttpUrl(URI)
            .queryParam("query", query)
            .queryParam("sort", sort)
            .queryParam("page", page)
            .queryParam("size", size)
            .encode()
            .toUriString();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "KakaoAK " + API_KEY);
        return headers;
    }
}
