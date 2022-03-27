package com.example.jpa.entity.compositekey.identifiability.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 부모
 */
@Entity
public class Parent {

    @Id
    @Column(name = "PARENT_ID")
    private String id;

    private String name;

}
