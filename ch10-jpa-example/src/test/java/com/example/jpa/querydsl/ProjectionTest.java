package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.dto.ProductDTO;
import com.example.jpa.entity.Product;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ProjectionTest {

    @BeforeAll
    static void insertProduct() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        IntStream.range(0, 5).forEach(i -> {
            Product product = new Product();
            product.setName("P_000" + i);
            product.setPrice(10000 * i);
            product.setStockAmount(10 * i);

            em.persist(product);
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("프로젝션 대상이 하나")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        List<String> result = queryFactory
                .select(product.name)
                .from(product)
                .fetch();

        result.forEach(s -> System.out.println("name = " + s));

        em.close();
    }


    @Test
    @DisplayName("여러 컬럼 반환")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        List<Tuple> tuples = queryFactory
                .select(product.name, product.price, product.stockAmount)
                .from(product)
                .fetch();

        tuples.forEach(t -> {
            System.out.println("name = " + t.get(product.name));
            System.out.println("price = " + t.get(product.price));
            System.out.println("stockAmount = " + t.get(product.stockAmount));
        });

        em.close();
    }

    @Test
    @DisplayName("프로퍼티 접근(Setter)")
    void test3() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        List<ProductDTO> tuples = queryFactory
                .select(Projections.bean(
                        ProductDTO.class,
                        product.name.as("productname"),
                        product.price,
                        product.stockAmount
                        ))
                .from(product)
                .fetch();

        tuples.forEach(p -> {
            System.out.println("name = " + p.getProductname());
            System.out.println("price = " + p.getPrice());
            System.out.println("stockAmount = " + p.getStockAmount());
        });

        em.close();
    }


    /*
        필드가 private로 설정해도 동작한다.
     */
    @Test
    @DisplayName("필드 직접 접근")
    void test4() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        List<ProductDTO> tuples = queryFactory
                .select(Projections.fields(
                        ProductDTO.class,
                        product.name.as("productname"),
                        product.price,
                        product.stockAmount
                ))
                .from(product)
                .fetch();

        tuples.forEach(p -> {
            System.out.println("name = " + p.getProductname());
            System.out.println("price = " + p.getPrice());
            System.out.println("stockAmount = " + p.getStockAmount());
        });

        em.close();
    }

    @Test
    @DisplayName("생성자 사용")
    void test5() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        List<ProductDTO> tuples = queryFactory
                .select(Projections.constructor(
                        ProductDTO.class,
                        product.name.as("productname"),
                        product.price,
                        product.stockAmount
                ))
                .from(product)
                .fetch();

        tuples.forEach(p -> {
            System.out.println("name = " + p.getProductname());
            System.out.println("price = " + p.getPrice());
            System.out.println("stockAmount = " + p.getStockAmount());
        });

        em.close();
    }
}
