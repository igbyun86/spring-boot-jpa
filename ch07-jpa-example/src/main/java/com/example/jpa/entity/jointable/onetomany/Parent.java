package com.example.jpa.entity.jointable.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "PARENT_ID"),          //현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID")     //반대방향 엔티티를 참조하는 외래키
    )
    private List<Child> child = new ArrayList<>();

}
