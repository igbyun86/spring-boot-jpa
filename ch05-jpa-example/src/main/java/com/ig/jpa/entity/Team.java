package com.ig.jpa.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    /**
     * 양방향 연관관계 추가
     * mappedBy의 값으로 사용된 team은 연관관계의 주인인 Member 엔티티의 team필드를 말한다. (Member.team)
     * 연관관계의 주인은 mappedBy 속성을 사용하지 않는다.
     * mappedBy 속성을 사용해서 속성의 값으로 연관관계의 주인을 지정해야 한다.(= 외래키 관리자를 선택하는것)
     * 연관관계
     */
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team() {
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
