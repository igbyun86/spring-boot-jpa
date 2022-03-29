package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Product;
import com.example.jpa.entity.QMember;
import com.example.jpa.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static com.example.jpa.entity.QMember.member;        //기본인스턴스

@SpringBootTest
public class QueryDSLTest {

    @Test
    @DisplayName("queryDSL")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        /*
            intellij에서
            Q~ class import안되는 경우 File -> Project Structure -> Module에서 설정
         */
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember qMember = new QMember("m");     //생성되는 JPQL의 별칭이 m
        queryFactory.from(qMember)
                .where(qMember.username.eq("회원1"))
                .orderBy(qMember.username.desc())
                .fetch();

        em.close();
    }

    @Test
    @DisplayName("queryDSL member 기본 인스턴스")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        //import static com.example.jpa.entity.QMember.member;        //기본인스턴스

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        queryFactory.from(member)
                .where(member.username.eq("회원1"))
                .orderBy(member.username.desc())
                .fetch();

        em.close();
    }


    @Test
    @DisplayName("검색 조건 조회")
    void test3() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;
        queryFactory.from(qProduct)
                .where(qProduct.name.eq("상품1").and(qProduct.price.eq(20000)))
                .fetch();

        em.close();
    }
}
