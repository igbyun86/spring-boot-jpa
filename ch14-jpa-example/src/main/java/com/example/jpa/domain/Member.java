package com.example.jpa.domain;

import com.example.jpa.BooleanToYNConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@Entity
//@Convert(converter = BooleanToYNConverter.class, attributeName = "vip")  //컨버터를 클래스 레벨에 설정(적용할 필드를 명시해야 한다.)
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    //Boolean을 YN으로 변환해주는 컨버터
    @Convert(converter = BooleanToYNConverter.class)
    private boolean vip;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
