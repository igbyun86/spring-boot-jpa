package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import com.example.jpa.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
public class DirectEntityTest {

    @Test
    @DisplayName("엔티티를 파라미터로 직접 받는 코드")
    void selectEntityParameterTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Member member = new Member();
        member.setId(1L);

        String query = "select m from Member m where m =:member";
        em.createQuery(query)
                .setParameter("member", member)
                .getResultList();

        tran.commit();
        em.close();

        /*
            select
                member0_.MEMBER_ID as member_i1_0_,
                member0_.AGE as age2_0_,
                member0_.TEAM_ID as team_id4_0_,
                member0_.NAME as name3_0_
            from MEMBER member0_
            where member0_.MEMBER_ID=?
         */
    }

    @Test
    @DisplayName("외래키 대신 엔티티를 파라미터로 직접 사용하는 코드")
    void selectFKEntityParameterTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Team team = new Team();
        team.setId(1L);

        String query = "select m from Member m where m.team =:team";
        em.createQuery(query)
                .setParameter("team", team)
                .getResultList();

        tran.commit();
        em.close();

        /*
            Hibernate:
            select
                member0_.MEMBER_ID as member_i1_0_,
                member0_.AGE as age2_0_,
                member0_.TEAM_ID as team_id4_0_,
                member0_.NAME as name3_0_
            from MEMBER member0_
            where member0_.TEAM_ID=?
         */
    }
}
