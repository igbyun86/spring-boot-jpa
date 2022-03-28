package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class PagingTest {


    @BeforeAll
    static void insertMember() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        IntStream.range(0, 100).forEach(i -> {
            Member member = new Member();
            member.setUsername("User" + i);
            member.setAge(i);

            em.persist(member);
        });

        tran.commit();
        em.close();
    }


    @Test
    @DisplayName("페이징 사용")
    void selectPagingTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);
        query.setFirstResult(10);   //조회 시작 위치
        query.setMaxResults(20);    //조회할 데이터 수

        List<Member> resultList = query.getResultList();
        System.out.println(resultList.size());
        resultList.forEach(m -> System.out.println("member =" + m.getUsername()));

        tran.commit();
        em.close();
    }
}
