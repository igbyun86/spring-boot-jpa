package com.example.jpa.entity.compositekey.identifiability.embeded;

import javax.persistence.*;

/**
 * 자식
 */
@Entity
public class Child {

    @MapsId("parentId")     //ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @EmbeddedId
    private ChildId childId;



}
