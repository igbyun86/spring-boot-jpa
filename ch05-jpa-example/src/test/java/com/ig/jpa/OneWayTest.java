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
public class OneWayTest {

    @Test
    public void 단방향_test() {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        /**
         * 회원과 팀을 저장하는 코드
         */
        insertRelation(em);

        /**
         * 조회
         */
        //객체 그래프 탐색
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam();   //객체 그래프 탐색
        System.out.println("팀 이름 = " + team.getName());     // 팀 이름 = 팀1

        //객체지향 쿼리 사용(JPQL)
        queryLogicJoin(em);


        /**
         * 연관관계 수정
         */
        updateRelation(em);

        /**
         * 연관관계 삭제
         */
        deleteRelation(em);

        tran.commit();
    }

    /**
     * 회원과 팀 저장
     * @param em
     */
    private static void insertRelation(EntityManager em) {
        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        //회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);     //연관관계 설정 member1 -> team1
        em.persist(member1);

        //회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);     //연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    /**
     * 객체지향 쿼리 사용(JPQL)
     * @param em
     */
    private static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member m "
                + "join m.team t "
                + "where t.name = :teamName ";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        resultList.forEach(m -> System.out.println("[query] member.username=" + m.getUsername()));
        //[query] member.username=회원1
        //[query] member.username=회원2
    }

    /**
     * 연관관계 수정
     */
    private static void updateRelation(EntityManager em) {
        //새로운 팀2
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        //회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);  //Hibernate: update Member set TEAM_ID=?, username=? where MEMBER_ID=?
    }

    /**
     * 연관관계 삭제
     * @param em
     */
    private static void deleteRelation(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);      //연관관계 제거
    }
}
