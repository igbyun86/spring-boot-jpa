package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.dto.ProductDTO;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootTest
public class UpdDelTest {

    @Test
    @DisplayName("수정 배치 쿼리")
    void updateTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        EntityTransaction tran = em.getTransaction();
        tran.begin();

        QProduct product = QProduct.product;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, product);

        updateClause.where(product.name.eq("상품1"))
                    .set(product.price, product.price.add(1000))
                    .execute();

        tran.commit();
        em.close();
    }


    @Test
    @DisplayName("삭제 배치 쿼리")
    void deleteTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        EntityTransaction tran = em.getTransaction();
        tran.begin();

        QProduct product = QProduct.product;
        JPADeleteClause deleteClause = new JPADeleteClause(em, product);
        deleteClause.where(product.name.eq("상품2"))
                .execute();

        tran.commit();
        em.close();
    }
}
