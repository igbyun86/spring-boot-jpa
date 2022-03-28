package com.example.jpa.loading;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.eager.Member;
import com.example.jpa.entity.eager.Team;
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
public class EagerLoadingTest {

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
        Team team = member.getTeam();   //객체그래프 탐색
        System.out.println(team.getName());

        tran.commit();
        em.close();

        /*
            Hibernate:
            select
                member0_.MEMBER_ID as member_i1_0_0_,
                member0_.name as name2_0_0_,
                member0_.TEAM_ID as team_id3_0_0_,
                team1_.TEAM_ID as team_id1_1_1_,
                team1_.name as name2_1_1_
            from
                MEMBER member0_
            inner join
                TEAM team1_
                    on member0_.TEAM_ID=team1_.TEAM_ID
            where
                member0_.MEMBER_ID=?
         */
    }

}
