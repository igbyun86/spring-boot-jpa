package com.example.jpa.entity.eager;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER) //즉시로딩
    @JoinColumn(name = "TEAM_ID", nullable = false) //nullable:false로 하면 left join을 inner 조인으로 변경한다.
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }

        this.team = team;

        //양방향 연관관계를 위한 리팩토링 코드
        if (team != null) team.getMembers().add(this);
    }
}
