package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class NamedNativeQueryTest {

    @BeforeAll
    static void insertProduct() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        IntStream.range(0, 5).forEach(i -> {
            Member member = new Member();
            member.setUsername("사용자" + i);
            member.setAge(20 + i);

            em.persist(member);
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("Named native 쿼리로 엔티티 조회")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        Query nativeQuery = em.createNamedQuery("Member.memberSQL", Member.class)
                .setParameter(1, 20);

        List<Member> resultList = nativeQuery.getResultList();
        resultList.forEach(m -> {
            System.out.println("username = " + m.getUsername());
            System.out.println("age = " + m.getAge());
        });

        em.close();
    }

    @Test
    @DisplayName("네이티브 sql xml 정의")
    void test3() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        // memberWithOrderCount는 Member.class에 설정
        List<Object[]> resultList = em.createNamedQuery("Member.memberWithOrderCountXml").getResultList();

        resultList.forEach(row -> {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("username = " + member.getUsername());
            System.out.println("age = " + member.getAge());
            System.out.println("orderCount = " + orderCount);
        });

        em.close();
    }

}
