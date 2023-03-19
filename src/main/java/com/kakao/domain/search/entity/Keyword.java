package com.kakao.domain.search.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long hits;

    @Builder
    public Keyword(Long id, String keyword, Long hits) {
        this.id = id;
        this.keyword = keyword;
        this.hits = hits;
    }

    public void increaseHits(){
        this.hits = hits + 1;
    }
}
