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
public class JoinTest {

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
    @DisplayName("inner join")
    void selectInnerJoinTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT m FROM Member m INNER JOIN m.team t "
                + "WHERE t.name = :teamName"
                ;

        String teamName = "teamA";
        List<Member> resultList = em.createQuery(query, Member.class)
                .setParameter("teamName", teamName)
                .getResultList();

        tran.commit();
        em.close();

        /*
        sql조인과 다르게 연관필드를 사용한다(m.team)
        select
            member0_.MEMBER_ID as member_i1_0_,
            member0_.AGE as age2_0_,
            member0_.TEAM_ID as team_id4_0_,
            member0_.NAME as name3_0_
        from
            MEMBER member0_
        inner join
            TEAM team1_
                on member0_.TEAM_ID=team1_.TEAM_ID
        where
            team1_.NAME=?
         */
    }


    @Test
    @DisplayName("inner join 서로 다른 타입의 엔티티")
    void selectInnerJoinEntityTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT m, t FROM Member m INNER JOIN m.team t";

        String teamName = "teamA";
        List<Object[]> resultList = em.createQuery(query)
                .getResultList();

        resultList.forEach(row -> {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("left join")
    void selectLeftJoinTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT m FROM Member m LEFT JOIN m.team t";
        List<Member> resultList = em.createQuery(query, Member.class)
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("컬렉션 조인")
    void selectCollectionJoinTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT m FROM Team t LEFT JOIN t.members m";
        List<Member> resultList = em.createQuery(query, Member.class)
                .getResultList();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("세타 조인")
    void selectCrossJionJoinTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT COUNT(m) FROM Member m, Team t WHERE m.username = t.name";
        Long count = em.createQuery(query, Long.class).getSingleResult();
        System.out.println(count);

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("left join on")
    void selectLeftJoinOnTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "" +
                "SELECT m, t FROM Member m " +
                "LEFT JOIN m.team t ON t.name = 'A'";
        List<Object[]> resultList = em.createQuery(query)
                .getResultList();

        tran.commit();
        em.close();
    }


    @Test
    @DisplayName("페치 조인")
    void selectInnerFetchJoinTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        // m만 선택했지만 연관된 team정보도 조회된다
        String query = "SELECT m FROM Member m INNER JOIN fetch m.team";
        List<Member> resultList = em.createQuery(query, Member.class)
                .getResultList();

        resultList.forEach(m -> {
            System.out.println("username = " + m.getUsername() + ", teamName = " + m.getTeam().getName());
        });

        tran.commit();
        em.close();

        /*
        Hibernate:
        select
            member0_.MEMBER_ID as member_i1_0_0_,
            team1_.TEAM_ID as team_id1_3_1_,
            member0_.AGE as age2_0_0_,
            member0_.TEAM_ID as team_id4_0_0_,
            member0_.NAME as name3_0_0_,
            team1_.NAME as name2_3_1_
        from
            MEMBER member0_
        inner join
            TEAM team1_
                on member0_.TEAM_ID=team1_.TEAM_ID
         */
    }

    @Test
    @DisplayName("컬렉션 페치 조인")
    void selectCollectionFetchJoinTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT t FROM Team t JOIN FETCH t.members where t.name = 'teamA'";
        List<Team> resultList = em.createQuery(query, Team.class)
                .getResultList();

        //team에 있는 memeber 수만큼 조회된다.

        resultList.forEach(t -> {
            System.out.println("teamname = " + t.getName() + ", team = " + t);

            t.getMembers().forEach(m -> {
                System.out.println("->username = " + m.getUsername() + ", member = " + m);
            });
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("컬렉션 페치 조인 distinct")
    void selectCollectionFetchJoinDistinctTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT distinct t FROM Team t JOIN FETCH t.members where t.name = 'teamA'";
        List<Team> resultList = em.createQuery(query, Team.class)
                .getResultList();

        //team에 있는 memeber 수만큼 조회된다.

        resultList.forEach(t -> {
            System.out.println("teamname = " + t.getName() + ", team = " + t);

            t.getMembers().forEach(m -> {
                System.out.println("->username = " + m.getUsername() + ", member = " + m);
            });
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("경로 표현식")
    void selectFieldTest() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT o.member.team FROM Order o where o.product.name = 'productA' and o.address.city = 'SEOUL'";
        em.createQuery(query)
                .getResultList();

        tran.commit();
        em.close();

        /*
        Hibernate:
            select
                team2_.TEAM_ID as team_id1_3_,
                team2_.NAME as name2_3_
            from ORDERS order0_ cross
            join MEMBER member1_
            inner join TEAM team2_
            on member1_.TEAM_ID=team2_.TEAM_ID cross
            join Product product3_
            where   order0_.MEMBER_ID=member1_.MEMBER_ID
            and     order0_.PRODUCT_ID=product3_.PRODUCT_ID
            and     product3_.NAME='productA'
            and     order0_.CITY='SEOUL'

         */
    }
}
