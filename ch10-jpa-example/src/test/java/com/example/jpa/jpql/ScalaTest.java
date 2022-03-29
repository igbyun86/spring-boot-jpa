package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
public class ScalaTest {


    @Test
    @DisplayName("날짜 함수")
    void selectDateTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        /*
            CURRENT_DATE: 현재날짜
            CURRENT_TIME: 현재시간
            CURRENT_TIMESTAMP: 현재 날짜 시간
         */

        em.createQuery("select CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP from Team t")
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("COALESCE")
    void selectCoalesceTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //t.name이 null이면 'teamZZZ'를 반환하라
        em.createQuery("select coalesce(t.name, 'teamZZZ') from Team t")
                .getResultList();

        tran.commit();
        em.close();
    }

}
