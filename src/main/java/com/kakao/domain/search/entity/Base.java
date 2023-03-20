package com.kakao.domain.search.entity;

import com.fasterxml.uuid.Generators;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * packageName : com.kakao.domain.search.entity
 * fileName    : Base
 * author      : ckr
 * date        : 2023/03/20
 * description :
 */

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class Base implements Serializable {

    private static final long serialVersionUID = 7003027247005884705L;

    @Id
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void createSequentialUUID() {
        //sequential uuid 생성
        UUID uuid = Generators.timeBasedGenerator().generate();
        String[] uuidArr = uuid.toString().split("-");
        String uuidStr = uuidArr[2]+uuidArr[1]+uuidArr[0]+uuidArr[3]+uuidArr[4];
        StringBuffer sb = new StringBuffer(uuidStr);
        sb.insert(8, "-");
        sb.insert(13, "-");
        sb.insert(18, "-");
        sb.insert(23, "-");
        uuid = UUID.fromString(sb.toString());
        this.id = uuid;
    }
}
