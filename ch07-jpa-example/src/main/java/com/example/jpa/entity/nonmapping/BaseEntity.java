package com.example.jpa.entity.nonmapping;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 테이블과 매핑할 필요가 없고 자식 엔티티에게 공통으로 사용되는 매핑정보만 제공
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
}
