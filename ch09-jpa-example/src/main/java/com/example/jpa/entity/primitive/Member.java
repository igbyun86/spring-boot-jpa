package com.example.jpa.entity.primitive;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;    //기본값 타입

    private int age;        //기본값 타입

}
