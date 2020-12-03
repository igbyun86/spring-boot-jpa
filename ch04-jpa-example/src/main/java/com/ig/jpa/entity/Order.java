package com.ig.jpa.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ORDER", uniqueConstraints = {
        @UniqueConstraint(
            name="ORDER_PROD_NO_UNIQUE",
            columnNames={"ORDER_NO", "PROD_NO"}

)})
public class Order {

    /**
     * GenerationType.IDENTITY: 기본키 생성을 데이터베이스에 위임
     * GenerationType.SEQUENCE: 데이터베이스 SEQUENCE사용
     * GenerationType.TABLE: 키생성 전용테이블을 만들어 데이터베이스 시퀀스를 흉내내는 전략
     * GenerationType.AUTO: 데이터베이스 방언에따라 전략 자동선택
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "PROD_NO")
    private String prodNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REG_DT")
    private Date regDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPD_DT")
    private Date updDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }
}
