package com.example.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
@Entity
@Table(name = "MEMBER")
@SqlResultSetMapping(name = "memberWithOrderCount",
        entities = {@EntityResult(entityClass = Member.class)},
        columns = {@ColumnResult(name = "ORDER_COUNT")}
)
@NamedNativeQuery(
        name = "Member.memberSQL",
        query = "SELECT MEMBER_ID, AGE, NAME, TEAM_ID " +
                "FROM MEMBER WHERE AGE > ?",
        resultClass = Member.class
        //resultSetMapping = "memberWithOrderCount"
)
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "NAME")
    private String username;        //상태 필드

    @Column(name = "AGE")
    private int age;                //상태 필드

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;              //연관 필드(단일 값 연관 필드)

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>(); //연관 필드(컬렌션 값 연관 필드)
}
