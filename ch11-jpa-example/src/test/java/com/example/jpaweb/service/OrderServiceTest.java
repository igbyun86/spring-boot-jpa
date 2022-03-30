package com.example.jpaweb.service;

import com.example.jpaweb.domain.Address;
import com.example.jpaweb.domain.Member;
import com.example.jpaweb.domain.Order;
import com.example.jpaweb.domain.OrderStatus;
import com.example.jpaweb.domain.item.Book;
import com.example.jpaweb.domain.item.Item;
import com.example.jpaweb.exception.NotEnoughStockException;
import com.example.jpaweb.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
    void 상품주문() {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);    //이름, 가격, 재고
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-456"));
        em.persist(member);
        return member;
    }

    @Test
    void 상품주문_재고수량초과() {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);    //이름, 가격, 재고
        int orderCount = 11;        //재고보다 많은 수량

        //Then
        assertThrows(NotEnoughStockException.class, () -> {
            //When
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    void 주문취소() {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);    //이름, 가격, 재고
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //When
        orderService.cancelOrder(orderId);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL이다.");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

    }

}
