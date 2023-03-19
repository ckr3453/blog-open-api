package com.kakao.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : KakaoBlogApiResponse
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Getter
public class KakaoBlogApiResponse {

    private Meta meta;

    private List<Document> documents;

    @Getter
    @Setter
    public static class Meta {
        private Boolean is_end;
        private Integer pageable_count;
        private Long total_count;
    }

    @Getter
    @Setter
    public static class Document {
        private String title;
        private String url;
        private String contents;
        private String blogname;
        private String thumbnail;
        private String datetime;
    }
}
