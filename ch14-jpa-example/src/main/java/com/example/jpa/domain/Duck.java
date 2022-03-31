package com.example.jpa.domain;

import com.example.jpa.DuckListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity
@EntityListeners(DuckListener.class)
public class Duck {

    @Id @GeneratedValue
    public Long id;

    private String name;



}
