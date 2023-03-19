package com.kakao.domain.search.dto;

import lombok.*;

import java.util.List;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : BlogResponse
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */
@Getter
@Builder
public class BlogResponse {
    private Integer page;
    private Integer size;
    private Long total;
    private List<Blog> blogs;

    @Getter
    @Builder
    public static class Blog {
        private String title;
        private String url;
        private String contents;
        private String blogName;
        private String thumbnail;
        private String postDate;
    }
}
