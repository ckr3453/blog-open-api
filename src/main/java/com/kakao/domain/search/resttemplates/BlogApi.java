package com.kakao.domain.search.resttemplates;

import com.kakao.domain.search.dto.BlogApiRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * packageName : com.kakao.domain.search.resttemplates
 * fileName    : ApiTemplate
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */
public interface BlogApi {
    ResponseEntity<?> get(BlogApiRequest blogApiRequest);
    String createURI(BlogApiRequest blogApiRequest);
    HttpHeaders createHeaders();
}
