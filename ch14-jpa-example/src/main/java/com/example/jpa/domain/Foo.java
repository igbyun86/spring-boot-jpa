package com.example.jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Foo {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @PrePersist
    public void prePersist() {
        System.out.println("Foo.prePersist id=" + id);     //id가 생성되기 전에 호출
    }

    @PostPersist
    public void postPersist() {
        System.out.println("Foo.postPersist id=" + id);    //id가 생성된 후 호출
    }

    @PostLoad
    public void postLoad() {
        System.out.println("Foo.postLoad");
    }

    @PostRemove
    public void postRemove() {
        System.out.println("Foo.postRemove");
    }

}
