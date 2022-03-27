package com.ig.jpa.entity.manytomany.twoway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT"                                  //연결 테이블을 지정한다.
            , joinColumns = {@JoinColumn(name = "MEMBER_ID")}           //현재 방향인 회원과 매핑할 조인 컬럼 정보를 지정한다.
            , inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")}   //반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정한다.
    )
    private List<Product> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
