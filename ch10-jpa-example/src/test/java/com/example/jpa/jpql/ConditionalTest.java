package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
public class ConditionalTest {

    @Test
    @DisplayName("Like 식")
    void selectLikeTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //중간에 원이라는 단어가 들어간 회원
        em.createQuery("select m from Member m where m.username like '%원%'")
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("컬렉션 식")
    void selectCollectionTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //주문이 하나라도 있는 회원
        //{컬렉션 값 연관 경로} IS [NOT] EMPTY
        em.createQuery("select m from Member m where m.orders is not empty ")
                .getResultList();

        tran.commit();
        em.close();
    }

}
