package com.kakao.domain.search.repository;

import com.kakao.domain.search.dto.KeywordResponse;
import com.kakao.domain.search.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.kakao.domain.search.repository
 * fileName    : KeywordRepository
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByKeyword(String keyword);
    @Query("SELECT new com.kakao.domain.search.dto.KeywordResponse(k.keyword, k.hits) FROM Keyword k order by k.hits desc")
    List<KeywordResponse> findTop10ByOrderByHitsDesc();
}
