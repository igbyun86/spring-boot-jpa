package com.example.jpa;

import com.example.jpa.entity.Product;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class ItemExpression {

    /**
     * 메소드 위임
     * @param product 대상 엔티티의 쿼리 타입
     * @param price 필요한 파라미터
     * @return
     */
    @QueryDelegate(Product.class)
    public static BooleanExpression isExpensive(QProduct product, Integer price) {
        return product.price.gt(price);
    }
}
