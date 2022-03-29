package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.QMember;
import com.example.jpa.entity.QOrder;
import com.example.jpa.entity.QProduct;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootTest
public class SubQueryTest {

    @Test
    @DisplayName("서브 쿼리 한건")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;
        QProduct productSub = new QProduct("productSub");

        queryFactory.from(product)
                .where(product.price.eq(
                        JPAExpressions.select(productSub.price.max())
                                .from(productSub)
                ))
                .fetch();

        em.close();

        /*
        select
            product0_.PRODUCT_ID as product_1_2_,
            product0_.NAME as name2_2_,
            product0_.PRICE as price3_2_,
            product0_.STOCKAMOUNT as stockamo4_2_
        from
            Product product0_
        where
            product0_.PRICE=(
                select
                    max(product1_.PRICE)
                from
                    Product product1_
            )
         */
    }

    @Test
    @DisplayName("서브 쿼리 여러건건")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;
        QProduct productSub = new QProduct("productSub");

        queryFactory.from(product)
                .where(product.in(
                        JPAExpressions.selectFrom(productSub)
                                .where(product.name.eq(productSub.name))
                ))
                .fetch();

        em.close();

        /*
        select
            product0_.PRODUCT_ID as product_1_2_,
            product0_.NAME as name2_2_,
            product0_.PRICE as price3_2_,
            product0_.STOCKAMOUNT as stockamo4_2_
        from
            Product product0_
        where
            product0_.PRODUCT_ID in (
                select
                    product1_.PRODUCT_ID
                from
                    Product product1_
                where
                    product0_.NAME=product1_.NAME
            )

         */
    }


}
