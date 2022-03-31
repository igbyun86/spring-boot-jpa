package com.springboot.jpa.service;

import com.springboot.jpa.domain.*;
import com.springboot.jpa.domain.item.Item;
import com.springboot.jpa.repository.MemberRepository;
import com.springboot.jpa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;

    /**
     * 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findById(memberId).orElse(new Member());
        Item item = itemService.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId
     */
    public void cancelOrder(long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElse(new Order());
        //주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     * @param orderSearch
     * @return
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch.toSpecification());
    }


}
