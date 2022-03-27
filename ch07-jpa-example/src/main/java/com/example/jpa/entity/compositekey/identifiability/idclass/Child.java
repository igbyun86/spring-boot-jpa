package com.example.jpa.entity.compositekey.identifiability.idclass;

import javax.persistence.*;

/**
 * 자식
 */
@Entity
@IdClass(ChildId.class)
public class Child {

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @Id @Column(name = "CHILD_ID")
    private String childId;



}
