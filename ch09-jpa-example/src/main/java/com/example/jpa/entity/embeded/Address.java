package com.example.jpa.entity.embeded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    @Column(name = "city")      //매핑할 컬럼 정의 가능
    private String city;
    private String street;
    private String state;

    @Embedded
    Zipcode zipcode;            //임베디드 타입 포함
}
