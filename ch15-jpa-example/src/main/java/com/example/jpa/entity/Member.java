package com.example.jpa.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@Entity
public class Member {

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    @Column(name = "NAME")
    private String name;        //상태 필드

    @Column(name = "AGE")
    private int age;                //상태 필드

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // name을 기준으로 equals 비교
    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Member)) return false;

        Member member = (Member) o;

        if (name != null ? !name.equals(member.getName()) : member.getName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
*/
}
