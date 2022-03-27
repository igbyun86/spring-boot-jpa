package com.example.jpa.entity.compositekey.identifiability.embeded;

import javax.persistence.*;

/**
 * 손자
 */
@Entity
public class GrandChild {


    @EmbeddedId
    private GrandChildId id;

    @MapsId("childId")      //GrandChildId.childId 매핑
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID")
    })
    private Child child;


    private String name;

}
