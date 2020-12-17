package com.ig.jpa;

import com.ig.jpa.entity.Member;
import com.ig.jpa.entity.Team;
import com.ig.jpa.util.JpaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootTest
public class TwoWayTest {

    @Test
    public void 양방향_test() {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        // 객체그래프 탐색
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();   // 팀->회원

        members.forEach(m -> System.out.println("member.username = " + m.getUsername()));


        tran.commit();
    }




}
