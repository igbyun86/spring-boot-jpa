package com.example.jpa;

import com.example.jpa.entity.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);


        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        //엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        //트랜잭션 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String id = "id1";
            Member member = new Member();
            member.setId(id);
            member.setUsername("big");
            member.setAge(12);

            //등록
            em.persist(member);

            //수정
            member.setAge(30);

            //한건 조회
            Member findMember = em.find(Member.class, id);
            System.out.printf("findMember=%s, age=%s", findMember.getUsername(), findMember.getAge());
            System.out.println();

            //목록 조회
            // JPQL은 데이터베이스 테이블을 대상으로 하지 않고 엔티티 객체를 대상으로 쿼리한다.
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            System.out.println("members.size=" + members.size());

            //삭제
            em.remove(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close();    //엔티티 매니저 팩토리 종료
    }



}
