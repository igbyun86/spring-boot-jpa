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
public class NativeSqlTest {

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

    /*
        네이티브 sql로 sql만 직접 사용할 뿐 나머지는 JPQL을 사용할 때와 같다.
        조회한 엔티티도 영속성 컨텍스트에서 관리된다.
     */
    @Test
    @DisplayName("native 쿼리로 엔티티 조회")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        String sql = "";
        sql += "SELECT MEMBER_ID, AGE, NAME, TEAM_ID ";
        sql += "FROM MEMBER WHERE AGE > ?";

        Query nativeQuery = em.createNativeQuery(sql, Member.class)
                .setParameter(1, 20);

        List resultList = nativeQuery.getResultList();
        System.out.println(resultList.size());

        em.close();
    }

    @Test
    @DisplayName("native 쿼리로 값 조회")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        String sql = "";
        sql += "SELECT MEMBER_ID, AGE, NAME, TEAM_ID ";
        sql += "FROM MEMBER WHERE AGE > :age";

        Query nativeQuery = em.createNativeQuery(sql)
                .setParameter("age", 20);

        List<Object[]> resultList = nativeQuery.getResultList();
        resultList.forEach(row -> {
            System.out.println("id = " + row[0]);
            System.out.println("age = " + row[1]);
            System.out.println("name = " + row[2]);
        });

        em.close();
    }

    @Test
    @DisplayName("결과 매핑 사용")
    void test3() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        String sql = "";
        sql += "SELECT M.MEMBER_ID, AGE, USERNAME, TEAM_ID, I.ORDER_COUNT ";
        sql += "FROM MEMBER M ";
        sql += "LEFT JOIN ";
        sql += "    (SELECT IM.MEMBER_ID, COUNT(1) AS ORDER_COUNT ";
        sql += "    FROM ORDERS O, MEMBER IM ";
        sql += "    WHERE O.MEMBER_ID = IM.MEMBER_ID) I ";
        sql += "ON M.MEMBER_ID = I.MEMBER_ID ";

        // memberWithOrderCount는 Member.class에 설정
        List<Object[]> resultList = em.createNativeQuery(sql, "memberWithOrderCount")
                .getResultList();

        /*
            ID, AGE, USERNAME, TEAM_ID는 Member 엔티티와 매핑하고
            ORDER_COUNT는 단순히 값으로 매핑한다.
         */
        resultList.forEach(row -> {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("member = " + member.toString());
            System.out.println("orderCount = " + orderCount);
        });

        em.close();
    }

    @Test
    @DisplayName("네이티브 sql과 페이징 처리")
    void test4() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        String sql = "";
        sql += "SELECT MEMBER_ID, AGE, NAME, TEAM_ID FROM MEMBER ";

        List resultList = em.createNativeQuery(sql)
                .setFirstResult(10)
                .setMaxResults(20)
                .getResultList();


        em.close();
    }

}
