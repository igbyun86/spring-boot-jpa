package com.ig.jpa;

import com.ig.jpa.entity.Member;
import com.ig.jpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootTest
public class TwoWayTest {

    @Test
    public void 양방향_조회_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        // 객체그래프 탐색
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();   // 팀->회원

        /**
         * team1.getMembers().add(member1);
         * team1.getMembers().add(member2);
         * 위 코드처럼 입력해주지 않아도 외래키는 주인이 관리하기 때문에 조회가 가능하다.
         */
        members.forEach(m -> System.out.println("member.username = " + m.getUsername()));


        tran.commit();
    }


    @Test
    public void 양방향_연관관계_저장_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        //회원1 저장
        Member member1 = new Member("member1", " 회원1");
        member1.setTeam(team1);
        em.persist(member1);

        //회원2 저장
        Member member2 = new Member("member2", " 회원2");
        member2.setTeam(team1);
        em.persist(member2);

        //team 리팩토링하기 전으로 테스트해야함
        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());
        // => members.size = 0

        tran.commit();
    }

    /**
     * Memeber에서 Team정보를 넣지 않는 테스트
     */
    @Test
    public void 순수한객체_양방향_test() {

        Team team1 = new Team("team1", "팀1");
        Member member1 = new Member("member1", " 회원1");
        Member member2 = new Member("member2", " 회원2");

        team1.getMembers().add(member1);    //연관관계 설정 team1 -> member1
        team1.getMembers().add(member2);    //연관관계 설정 team1 -> member2

        member1.setTeam(team1); //연관관계 설정 member1 -> team1
        member2.setTeam(team1); //연관관계 설정 member2 -> team1

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());

        // => members.size = 2
    }

    /**
     * 객체의 양방향과 같이 양쪽 모두 값을 넣어 연관관계 설정하는 테스트
     */
    @Test
    public void ORM_양방향_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", " 회원1");

        //양방향 연관관계 설정
        member1.setTeam(team1);             //연관관계 설정 member1 -> team1  (연관관계 주인)
        team1.getMembers().add(member1);    //연관관계 설정 team1 -> member1  (주인이 아니다. 저장시 사용되지 않는다.)
        em.persist(member1);

        Member member2 = new Member("member2", " 회원2");

        //양방향 연관관계 설정
        member2.setTeam(team1);             //연관관계 설정 member2 -> team1
        team1.getMembers().add(member2);    //연관관계 설정 team1 -> member2
        em.persist(member2);

        tran.commit();
    }

    /**
     * Memeber에서 Team을 등록할때 team에서도 member정보를 등록할수 있게
     * 리팩토링한 코드 테스트
     */
    @Test
    public void ORM_양방향_리팩토링_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        //회원1 저장
        Member member1 = new Member("member1", " 회원1");
        member1.setTeam(team1);     //양방향 설정
        em.persist(member1);

        //회원2 저장
        Member member2 = new Member("member2", " 회원2");
        member2.setTeam(team1);     //양방향 설정
        em.persist(member2);

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());
        // => members.size = 2

        tran.commit();
    }


}

