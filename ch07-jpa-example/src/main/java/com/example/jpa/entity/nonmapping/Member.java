package com.example.jpa.entity.nonmapping;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))
public class Member extends BaseEntity {

    //ID 상속
    //NAME 상속

    private String email;
}
