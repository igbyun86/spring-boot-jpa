package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
public class DynamicQueryTest {

    @Test
    @DisplayName("동적 쿼리")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        //검색 파라미터
        SearchParam param = new SearchParam();
        param.setName("상품ZZZ");
        param.setPrice(10000);

        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isNullOrEmpty(param.getName())) {
            builder.and(product.name.contains(param.getName()));
        }

        if (param.getPrice() != null) {
            builder.and(product.price.gt(param.getPrice()));
        }

        JPAQueryFactory query = new JPAQueryFactory(em);
        query.from(product)
                .where(builder)
                .fetch();

        em.close();
    }

    @Test
    @DisplayName("메소드 위임")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        //검색 파라미터
        SearchParam param = new SearchParam();
        param.setName("상품ZZZ");
        param.setPrice(10000);

        QProduct product = QProduct.product;

        JPAQueryFactory query = new JPAQueryFactory(em);
        query.from(product)
                .where(product.isExpensive(param.getPrice()))
                .fetch();

        em.close();
    }

}
