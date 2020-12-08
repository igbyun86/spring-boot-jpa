package com.ig.jpa.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;          //아이디

    /**
     * not null 제약조건 추가
     * varchar 길이 설정(default:255)
     */
    @Column(name = "NAME", nullable = false, length = 10)
    private String username;    //이름

    private Integer age;        //나이

    /**
     * EnumType.ORDINAL enum에 정의된 순서대로 저장
     * EnumType.STRING enum 이름 그대로 저장(추천)
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /**
     * TemporalType.DATE 날짜
     * TemporalType.TIME 시간
     * TemporalType.TIMESTAMP 날짜와 시간
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    /**
     * 이 필드는 매핑하지 않는다.
     */
    @Transient
    private String temp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

