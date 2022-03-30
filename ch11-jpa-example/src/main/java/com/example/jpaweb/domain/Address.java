package com.example.jpaweb.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;


}
