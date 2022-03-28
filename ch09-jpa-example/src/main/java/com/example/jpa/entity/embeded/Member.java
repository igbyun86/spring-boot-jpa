package com.example.jpa.entity.embeded;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /*
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date endDate;
    */

    //근무기간이란 임베디드 타입 생성
    @Embedded
    Period workPeriod;

    /*
    private String city;
    private String street;
    private String zipcode;
    */

    //주소 임베디드 타입 생성
    @Embedded
    Address homeAddress;


    /*
        Address의 매핑정보를 재정의
        임베디드 타입이 null이면 매핑한 컬럼값도 모두 null이 된다.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
    })
    Address companyAddress;
}
