package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.QMember;
import com.example.jpa.entity.QOrder;
import com.example.jpa.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootTest
public class JoinTest {

    @Test
    @DisplayName("기본 조인")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QProduct product = QProduct.product;

        queryFactory.from(order)
                .join(order.member, member)
                .leftJoin(order.product, product)
                .fetch();

        em.close();
    }

    @Test
    @DisplayName("조인 on 사용")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QProduct product = QProduct.product;

        queryFactory.from(order)
                .leftJoin(order.product, product)
                .on(product.price.gt(20000))    //on 추가
                .fetch();

        em.close();
    }

    @Test
    @DisplayName("페치 조인 사용")
    void test3() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QProduct product = QProduct.product;

        queryFactory.from(order)
                .innerJoin(order.member, member).fetchJoin()
                .leftJoin(order.product, product).fetchJoin()
                .fetch();

        em.close();
    }

    @Test
    @DisplayName("from 절에 여러 조건 사용")
    void test4() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        queryFactory.from(order, member)
                .where(order.member.eq(member))
                .fetch();

        em.close();

        /*
        select
            order0_.id as id1_1_,
            order0_.CITY as city2_1_,
            order0_.STREET as street3_1_,
            order0_.ZIPCODE as zipcode4_1_,
            order0_.MEMBER_ID as member_i6_1_,
            order0_.ORDERAMOUNT as orderamo5_1_,
            order0_.PRODUCT_ID as product_7_1_
        from
            ORDERS order0_ cross
        join
            MEMBER member1_
        where
            order0_.MEMBER_ID=member1_.MEMBER_ID
         */
    }
}
