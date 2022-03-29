package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.QueryModifiers;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootTest
public class PagingSortTest {

    @Test
    @DisplayName("페이징")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;
        queryFactory.from(qProduct)
                .where(qProduct.price.gt(20000))
                .orderBy(qProduct.price.desc(), qProduct.stockAmount.asc())
                .offset(10).limit(20)
                .fetch();

        em.close();
    }

    @Test
    @DisplayName("페이징 QueryModifiers 사용")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;

        QueryModifiers queryModifiers = new QueryModifiers(20L, 10L);//limit, offset

        queryFactory.from(qProduct)
                .where(qProduct.price.gt(20000))
                .orderBy(qProduct.price.desc(), qProduct.stockAmount.asc())
                .restrict(queryModifiers)
                .fetch();

        em.close();
    }

}
