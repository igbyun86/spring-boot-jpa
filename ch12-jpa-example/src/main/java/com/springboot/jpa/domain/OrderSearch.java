package com.springboot.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static com.springboot.jpa.domain.OrderSpec.memberNameLike;
import static com.springboot.jpa.domain.OrderSpec.orderStatusEq;
import static org.springframework.data.jpa.domain.Specification.where;

@Getter @Setter
public class OrderSearch {
    private String memberName;      //회원 이름
    private OrderStatus orderStatus;//주문 상태

    public Specification<Order> toSpecification() {
        return where(memberNameLike(memberName).and(orderStatusEq(orderStatus)));
    }
}
