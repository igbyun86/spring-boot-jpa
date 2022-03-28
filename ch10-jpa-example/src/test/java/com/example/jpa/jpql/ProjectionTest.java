package com.example.jpa.jpql;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.dto.UserDTO;
import com.example.jpa.entity.Address;
import com.example.jpa.entity.Member;
import com.example.jpa.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ProjectionTest {

    @BeforeAll
    static void insertMember() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        IntStream.range(0, 5).forEach(i -> {
            Member member = new Member();
            member.setUsername("User" + i);
            member.setAge(30 + i);

            em.persist(member);
        });

        tran.commit();
        em.close();
    }

    /*
        임베디드 타입은 엔티티 타입이 아닌 값 타입이다.
        따라서 이렇게 직접 조회한 임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.
     */
    @Test
    void 임베디드_타입_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT o.address FROM Order o";
        List<Address> resultList = em.createQuery(query, Address.class)
                .getResultList();

        /*
        Hibernate:
            select
                order0_.CITY as col_0_0_,
                order0_.STREET as col_0_1_,
                order0_.ZIPCODE as col_0_2_
            from
                ORDERS order0_
         */

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("여러 프로젝션 Object[] 조회")
    void 여러_프로젝션_Object_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT m.username, m.age FROM Member m";
        List<Object[]> resultList = em.createQuery(query)
                .getResultList();

        resultList.forEach(arr -> {
            String username = (String) arr[0];
            int age = (int) arr[1];

            System.out.println("username = "+ username + " / age = " + age);
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("여러 프로젝션 엔티티 타입 조회")
    void 여러_프로젝션_entity_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String query = "SELECT o.member, o.product, o.orderAmount FROM Order o";
        List<Object[]> resultList = em.createQuery(query)
                .getResultList();

        resultList.forEach(row -> {
            Member member = (Member) row[0];
            Product product = (Product) row[1];
            int orderAmount = (int) row[2];
        });

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("NEW 명령어 사용전")
    void before_new_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        List<Object[]> resultList = em.createQuery("SELECT m.username, m.age FROM Member m")
                .getResultList();

        List<UserDTO> userDTOs = new ArrayList<>();
        resultList.forEach(row -> {
            UserDTO userDTO = new UserDTO((String) row[0], (Integer) row[1]);
            userDTOs.add(userDTO);
        });

        tran.commit();
        em.close();
    }

    /*
        주의점
        -패키지명을 포함한 전체 클래스 명을 입력해야 한다.
        -순서와 타입이 일치하는 생성자가 필요하다.
     */
    @Test
    @DisplayName("NEW 명령어 사용후")
    void after_new_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        TypedQuery<UserDTO> query = em.createQuery("SELECT new com.example.jpa.dto.UserDTO(m.username, m.age) FROM Member m", UserDTO.class);

        List<UserDTO> resultList = query.getResultList();
        resultList.forEach(row -> {
            System.out.println("username = "+ row.getUsername() + " / age = " + row.getAge());
        });

        tran.commit();
        em.close();
    }
}
