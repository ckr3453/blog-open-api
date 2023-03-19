package com.kakao.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : NaverBlogApiResponse
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */

@Getter
public class NaverBlogApiResponse {
    private String lastBuildDate;
    private Long total;
    private Integer start;
    private Integer display;
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private String postdate;
    }
}
