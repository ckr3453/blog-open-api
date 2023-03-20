package com.kakao.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : BlogApiRequest
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */
@Getter
@Setter
public class BlogApiRequest {

    public BlogApiRequest(String query, String sort, Integer page, Integer size) {
        this.query = query;
        this.sort = "recency".equals(sort) ? sort : "accuracy";
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
    }

    @NotBlank(message = "query must have a value")
    private String query;
    private String sort;
    @DecimalMax(value = "50", message = "page is more than max")
    private Integer page;
    @DecimalMax(value = "50", message = "size is more than max")
    private Integer size;
}
