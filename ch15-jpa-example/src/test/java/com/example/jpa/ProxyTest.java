package com.example.jpa;


import com.example.jpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProxyTest {


    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void insertMember() {

    }


    @Test
    void 영속성컨텍스트와_프록시() {
        Member member = new Member("member1", "회원1");
        em.persist(member);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, "member1");
        Member findMember = em.find(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());
        /*
        refMember Type = class com.example.jpa.entity.Member$HibernateProxy$IpK4Sjz8
        findMember Type = class com.example.jpa.entity.Member$HibernateProxy$IpK4Sjz8
        */

        // 영속성 컨텍스트와 프록시 동일성 비교
        assertTrue(refMember == findMember); //true
    }


    @Test
    @DisplayName("원본 먼저 조회하고 나서 프록시로 조회하기")
    void 영속성컨텍스트와_프록시2() {
        Member newMember = new Member("member1", "회원1");
        em.persist(newMember);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, "member1");
        Member refMember = em.getReference(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());
        /*
        refMember Type = class com.example.jpa.entity.Member
        findMember Type = class com.example.jpa.entity.Member
        */

        // 영속성 컨텍스트와 프록시 동일성 비교
        assertTrue(refMember == findMember); //true
    }

    @Test
    void 프록시_타입비교() {
        Member newMember = new Member("member1", "회원1");
        em.persist(newMember);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        //refMember Type = class com.example.jpa.entity.Member$HibernateProxy$pg6fQIiU

        assertFalse(Member.class == refMember.getClass());      //false(부무클래스와 자식 클래스를 ==비교 했으므로 false)
        assertTrue(refMember instanceof Member);                        //true(프록시는 원본 엔티티의 자식 타입이므로 true)
    }


    @Test
    @DisplayName("name을 기준으로 비교")
    void 프록시와_동등성비교() {
        Member saveMember = new Member("member1", "회원1");
        em.persist(saveMember);
        em.flush();
        em.clear();

        Member newMember = new Member("member1", "회원1");
        Member refMember = em.getReference(Member.class, "member1");

        assertTrue(refMember instanceof Member);
        assertTrue(newMember.equals(refMember));
    }


}
