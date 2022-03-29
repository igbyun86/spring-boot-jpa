package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import com.example.jpa.entity.Team;
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
public class SubQueryTest {


    @BeforeAll
    static void insertMember() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Team team1 = new Team();
        team1.setName("teamA");

        em.persist(team1);

        Team team2 = new Team();
        team2.setName("teamB");

        IntStream.range(0, 5).forEach(i -> {
            Member member = new Member();
            member.setUsername("User" + i);
            member.setAge(30 + i);

            member.setTeam(team1);
            team1.getMembers().add(member);

            em.persist(member);
        });

        tran.commit();
        em.close();
    }


    @Test
    @DisplayName("서브쿼리 조회 테스트")
    void selectSubQueryTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
/*
        //나이가 평균보다 많은 회원
        em.createQuery("select m from Member m where m.age > (select avg(m2.age) from Member m2)")
                .getResultList();
*/

        //한 건이라도 주문한 고객
        em.createQuery("select m from Member m where (select count(o) from Order o where m = o.member) > 0")
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("서브쿼리 EXISTS 조회 테스트")
    void selectSubQueryExistsTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //팀A 소속인 회원
        em.createQuery("select m from Member m where exists (select t from m.team t where t.name = '팀A')")
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("서브쿼리 ALL 조회 테스트")
    void selectSubQueryAllTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //전체 상품 각각의 재고보다 주문량이 많은 주문들
        em.createQuery("select o from Order o where o.orderAmount > ALL (select p.stockAmount from Product p)")
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("서브쿼리 ANY 조회 테스트")
    void selectSubQueryAnyTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //어떤 팀이든 팀에 소속된 회원
        em.createQuery("select m from Member m where m.team = ANY (select t from Team t)")
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("서브쿼리 IN 조회 테스트")
    void selectSubQueryInTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //20세 이상을 보유한 팀
        em.createQuery("select t from Team t where t IN (select t2 from Team t2 join t2.members m2 where m2.age > 20)")
                .getResultList();

        tran.commit();
        em.close();
    }
}
