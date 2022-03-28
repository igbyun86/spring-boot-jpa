package com.example.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
//@Entity
//@Table(name = "MEMBER")
public class Member {


    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String name;

}
