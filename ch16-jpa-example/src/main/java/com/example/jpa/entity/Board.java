package com.example.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Board {

    @Id
    private String id;
    private String title;

    @Version
    private Integer version;

}
