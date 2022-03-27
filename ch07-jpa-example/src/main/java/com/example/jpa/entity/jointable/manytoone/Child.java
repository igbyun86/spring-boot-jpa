package com.example.jpa.entity.jointable.manytoone;

import javax.persistence.*;

@Entity
public class Child {

    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "CHILD_ID"),          //현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "PARENT_ID")     //반대방향 엔티티를 참조하는 외래키

    )
    private Parent parent;

}
