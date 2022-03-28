package com.example.jpa.loading;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.lazy.Member;
import com.example.jpa.entity.lazy.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * 테스트시 entity 주석 해제하고 테스트할 것
 */
@SpringBootTest
public class LazyLoadingTest {

    @BeforeAll
    static void insertTeam_Member() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Team team = new Team();
        team.setId("tId1");
        team.setName("team1");
        em.persist(team);

        Member member = new Member();
        member.setId("id1");
        member.setName("igbyun");
        member.setTeam(team);

        em.persist(member);

        tran.commit();
        em.close();
    }

    @Test
    void 즉시로딩_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Member member = em.find(Member.class, "id1");
        Team team = member.getTeam();           //객체그래프 탐색(반환된 team은 프록시 객체)
        System.out.println(team.getName());     //팀 객체 실제 사용

        tran.commit();
        em.close();

        /*
        Hibernate:
            select
                member0_.MEMBER_ID as member_i1_0_0_,
                member0_.name as name2_0_0_,
                member0_.TEAM_ID as team_id3_0_0_
            from
                MEMBER member0_
            where
                member0_.MEMBER_ID=?
        Hibernate:
            select
                team0_.TEAM_ID as team_id1_1_0_,
                team0_.name as name2_1_0_
            from
                TEAM team0_
            where
                team0_.TEAM_ID=?
         */
    }
}
