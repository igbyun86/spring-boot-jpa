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
public class GroupTest {

    @Test
    @DisplayName("groupBy 사용")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;

        queryFactory.from(qProduct)
                .groupBy(qProduct.price)
                .having(qProduct.price.gt(1000))
                .fetch();

        em.close();

        /*
        Hibernate:
            select
                product0_.PRODUCT_ID as product_1_2_,
                product0_.NAME as name2_2_,
                product0_.PRICE as price3_2_,
                product0_.STOCKAMOUNT as stockamo4_2_
            from
                Product product0_
            group by
                product0_.PRICE
            having
                product0_.PRICE>?
         */
    }

}
