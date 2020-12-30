package com.ig.jpa.entity;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    //연관관계 설정
    public void setTeam(Team team) {
        /**
         * team 등록이전에 team정보가 이미 있다면 등록된 member정보를 삭제하고
         * 새로운 team에 member정보를 등록한다.
         * member.setTeam(team1);
         * member.setTeam(team2);   => 아래 코드가 없다면 team1에 불필요한 member정보가 그대로 남아있음.
         */
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }

        this.team = team;

        //양방향 연관관계를 위한 리팩토링 코드
        team.getMembers().add(this);
    }
}
