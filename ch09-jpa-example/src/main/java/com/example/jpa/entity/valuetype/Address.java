package com.example.jpa.entity.valuetype;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "city")      //매핑할 컬럼 정의 가능
    private String city;
    private String street;
    private String state;
    private String zipcode;
}
