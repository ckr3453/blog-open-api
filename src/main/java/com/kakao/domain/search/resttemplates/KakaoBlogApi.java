package com.kakao.domain.search.resttemplates;

import com.kakao.domain.search.dto.BlogApiRequest;
import com.kakao.domain.search.dto.KakaoBlogApiResponse;
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
public class KakaoBlogApi implements BlogApi {
    private final RestTemplate restTemplate;

    @Value("${kakao.blog-search-uri}")
    private String URI;

    @Value("${kakao.api-key}")
    private String API_KEY;

    @Override
    public ResponseEntity<KakaoBlogApiResponse> get(BlogApiRequest blogApiRequest){
        return restTemplate.exchange(
            createURI(blogApiRequest),
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            KakaoBlogApiResponse.class
        );
    }

    @Override
    public String createURI(BlogApiRequest blogApiRequest) {
        return UriComponentsBuilder.fromHttpUrl(URI)
            .queryParam("query", blogApiRequest.getQuery())
            .queryParam("sort", blogApiRequest.getSort())
            .queryParam("page", blogApiRequest.getPage())
            .queryParam("size", blogApiRequest.getSize())
            .encode()
            .toUriString();
    }

    @Override
    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "KakaoAK " + API_KEY);
        return headers;
    }
}
