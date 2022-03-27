package com.ig.jpa.entity.manytomany.linkentity;

import java.io.Serializable;

public class MemberProductId implements Serializable {

    private String member;      //MemberProduct.member와 연결
    private String product;     //MemberProduct.product와 연결

    //hashCode and equals


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
