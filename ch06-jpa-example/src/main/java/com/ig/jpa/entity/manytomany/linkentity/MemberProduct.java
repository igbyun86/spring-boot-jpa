package com.ig.jpa.entity.manytomany.linkentity;

import javax.persistence.*;

@Entity
@IdClass(MemberProductId.class)     //복합기본키 사용
public class MemberProduct {

    @Id @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;      //MemberProductId.member와 연결

    @Id @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;    //MemberProductId.product와 연결

    private int orderAmount;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
