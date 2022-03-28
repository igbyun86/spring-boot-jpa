package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class JpqlTest {

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
    void selectTypeQuery() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);

        List<Member> resultList = query.getResultList();
        resultList.forEach(m -> System.out.println("member =" + m));

        tran.commit();
        em.close();
    }

}
