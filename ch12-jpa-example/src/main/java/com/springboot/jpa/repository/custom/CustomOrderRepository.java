package com.springboot.jpa.repository.custom;

import com.springboot.jpa.domain.Order;
import com.springboot.jpa.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {

    public List<Order> search(OrderSearch orderSearch);

}
