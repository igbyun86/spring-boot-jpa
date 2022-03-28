package com.example.jpa.proxy;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 테스트시 entity 주석 해제하고 테스트할 것
 */
@SpringBootTest
public class ProxyTest {

    @BeforeAll
    static void insertMember() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        em.persist(new Member("id1", "igbyun"));

        tran.commit();
        em.close();
    }

    @Test
    void 준영속상태_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        // 등록된 row가 있어야함.
        Member member = em.getReference(Member.class, "id1");

        tran.commit();
        em.close();

        assertThrows(LazyInitializationException.class, ()-> {
            System.out.println(member.getName());
        });
    }

    @Test
    void 초기화여부_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        // 등록된 row가 있어야함.
        Member member = em.getReference(Member.class, "id1");

        boolean isLoad = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(member);

        assertFalse(isLoad); //초기화 false

        //member 실행 - 초기화
        member.getName();

        isLoad = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(member);

        assertTrue(isLoad);  //초기화 true

        // proxy 클래스
        System.out.println("memberProxy = " + member.getClass().getName());

        tran.commit();
        em.close();
    }

}
