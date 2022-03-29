package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
public class NamedTest {

    @Test
    @DisplayName("Named 쿼리 xml에 정의")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String username = "user";

        /*
            쿼리는 XML로 작성
            xml은 persistence.xml에 설정을 추가해준다.
         */
        em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();

        tran.commit();
        em.close();

    }


}
