package com.example.jpa;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.Order;
import com.example.jpa.entity.OrderStatus;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
public class LazyLoadingTest {


    @PersistenceContext
    EntityManager em;


    @BeforeEach
    void insertMember_Order() {
        Member member = new Member();
        member.setId("member1");
        member.setName("회원1");

        em.persist(member);

        Member member2 = new Member();
        member2.setId("member2");
        member2.setName("회원2");

        em.persist(member2);

        Order order1 = new Order();
        order1.setStatus(OrderStatus.ORDER);
        order1.setOrderDate(Date.valueOf(LocalDate.now()));
        order1.setMember(member);
        em.persist(order1);

        Order order2 = new Order();
        order2.setStatus(OrderStatus.ORDER);
        order2.setOrderDate(Date.valueOf(LocalDate.now()));
        order2.setMember(member);
        em.persist(order2);

        em.flush();
        em.clear();
    }

    @Test
    void member조회_find() {
        Member member = em.find(Member.class, "member1");
    }
    
    @Test
    @DisplayName("즉시 로딩과 N+1문제 발생 테스트(즉시로딩)")
    void member조회_jpql_test1() {
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        //memeber 수만큼 select * from order where memeber_id = ? 쿼리가 실행된다.
    }

    @Test
    @DisplayName("즉시 로딩과 N+1문제 발생 테스트(지연로딩)")
    void member조회_jpql_test2() {
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        /*
            주문 컬렉션을 초기화하는 수만큼 sql이 실행된다.
            select * from order where memeber_id = 1
            select * from order where memeber_id = 2
            ...
         */
        members.forEach(m -> {
            System.out.println("member = " + m.getOrders().size()); //지연로딩 초기화
        });
    }

    @Test
    void 페치_조인_사용() {
        /*
            일대다 조인이므로 DISTINCT를 사용해서 중복을 제거한다.
         */
        List<Member> members = em.createQuery("select distinct m from Member m join fetch m.orders", Member.class)
                .getResultList();

        members.forEach(m -> {
            System.out.println("member = " + m.getOrders().size()); //지연로딩 초기화
        });
    }



}
