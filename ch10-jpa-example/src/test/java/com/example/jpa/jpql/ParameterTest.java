package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ParameterTest {

    @BeforeAll
    static void insertMember() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        IntStream.range(0, 5).forEach(i -> {
            Member member = new Member();
            member.setUsername("User" + i);
            member.setAge(30 + i);

            em.persist(member);
        });

        tran.commit();
        em.close();
    }


    @Test
    void 이름_기준_파라미터_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String usernameParam = "User1";
        List<Member> resultList = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class)
                .setParameter("username", usernameParam)
                .getResultList();

        resultList.forEach(m -> System.out.println("member =" + m));

        tran.commit();
        em.close();
    }


    @Test
    void 위치_기준_파라미터_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String usernameParam = "User3";
        List<Member> resultList = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
                .setParameter(1, usernameParam)
                .getResultList();

        resultList.forEach(m -> System.out.println("member =" + m));

        tran.commit();
        em.close();
    }
}
