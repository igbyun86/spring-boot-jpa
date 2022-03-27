package com.example.jpa.entity.compositekey.unidentifiability.embeded;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;

@Entity
@IdClass(ParentId.class)
public class Parent {

    @EmbeddedId
    private String id;


    private String name;

}
