package com.ig.jpa;

import com.ig.jpa.entity.Member;
import com.ig.jpa.entity.Order;
import com.ig.jpa.util.JpaUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);

        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Member member = new Member();
        Order order = new Order();
        order.setOrderNo("A0001");
        order.setProdNo("P0001");

        em.persist(order);

        System.out.println("T_ORDER.id: " + order.getId());

        tran.commit();

    }

}
