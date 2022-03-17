package com.ig.jpa;

import com.ig.jpa.entity.Member;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Service
public class ExamMerge {

    public void merge() {
        Member member = createMember("memberA", "회원1");
        member.setUsername("회원명변경");

        mergeMember(member);
    }

    private static Member createMember(String id, String username) {
        //==영속성 컨텍스트1 시작==//
        EntityManager em1 = EntityManagerFactoryHelper.getInstance().createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        em1.persist(member);
        tx1.commit();

        em1.close();    //영속성 컨텍스트1 종료(member 엔티티는 준영속상태가 된다.)
        //==영속성 컨텍스트1 종료==//

        return member;
    }

    private static void mergeMember(Member member) {
        //==영속성 컨텍스트2 시작==//
        EntityManager em2 = EntityManagerFactoryHelper.getInstance().createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        //member엔티티가 준영속 상태에서 영속상태로 변경되는것이 아니고 mergeMember라는 새로운 영속상태의 엔티티가 반환된다.
        Member mergeMember = em2.merge(member);
        tx2.commit();

        //준영속 상태
        System.out.println("member = " + member.getUsername());

        //영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());

        System.out.println("em2 contains member = " + em2.contains(member));
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));

        em2.close();
        //==영속성 컨텍스트2 종료==//
    }
}
