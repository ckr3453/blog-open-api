package com.kakao.domain.search.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * packageName : com.kakao.domain.search.entity
 * fileName    : Keywords
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends Base {

    private static final long serialVersionUID = -8324275265009835379L;

    @Column(nullable = false, length=100)
    private String keyword;

    @Column(nullable = false)
    private Long hits;

    @Builder
    public Keyword(String keyword, Long hits) {
        this.keyword = keyword;
        this.hits = hits;
    }

    public void increaseHits(){
        this.hits = hits + 1;
    }
}
