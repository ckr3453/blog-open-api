package com.kakao.domain.search.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : BlogApiRequest
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */
@Getter
public class BlogApiRequest {

    public BlogApiRequest(String query, String sort, Integer page, Integer size) {
        this.query = query;
        this.sort = "recency".equals(sort) ? sort : "accuracy";
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
    }

    @NotBlank(message = "query must have a value")
    @Size(min=1, max=100, message = "query length between 1 and 100")
    private String query;
    private String sort;
    @Max(value = 50, message = "page is more than max")
    private Integer page;
    @Max(value = 50, message = "size is more than max")
    private Integer size;
}
