package com.kakao.domain.search.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.kakao.domain.search.dto
 * fileName    : KakaoBlogApiResponse
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Getter
public class BlogResponse {

    private Meta meta;

    private Documents[] documents;

    @Getter
    @Setter
    private static class Meta {
        boolean is_end;
        int pageable_count;
        int total_count;
    }

    @Getter
    @Setter
    private static class Documents {
        String blogname;
        String contents;
        String datetime;
        String thumbnail;
        String title;
        String url;
    }
}
