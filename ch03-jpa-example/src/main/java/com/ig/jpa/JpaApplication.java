package com.ig.jpa;

import com.ig.jpa.entity.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);


        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();

        //엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        //트랜잭션 획득
        EntityTransaction tx = em.getTransaction();

        try {
            // 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
            tx.begin();

            String id = "id1";
            Member member = new Member();
            member.setId(id);
            member.setUsername("Tom");
            member.setAge(10);

            //등록
            em.persist(member);

            String id2 = "id2";
            Member member2 = new Member();
            member2.setId(id2);
            member2.setUsername("Bob");
            member2.setAge(20);

            //등록
            em.persist(member2);

            Member memberA = em.find(Member.class, "id1");
            Member memberB = em.find(Member.class, "id1");

            //영속 엔티티의 동일성 보장(A와 B는 같은 인스턴스)
            System.out.println("동일성: " + String.valueOf(memberA == memberB));

            member.setAge(30);


            //한건 조회
            Member findMember = em.find(Member.class, id);
            System.out.printf("findMember=%s, age=%s", findMember.getUsername(), findMember.getAge());
            System.out.println();

            //목록 조회
            //JPQL은 데이터베이스 테이블을 대상으로 하지 않고 엔티티 객체를 대상으로 쿼리한다.
            //JPQL을 사용하는 경우도 flush가 발생한다.
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            System.out.println("members.size=" + members.size());

            //삭제
            em.remove(member);

            //준영속 상태로 변경(영속성 컨텍스트에게 해당 엔티티를 관리하지 말라는 것)
            //준영속 상태가되면 1차캐시, 쓰기 지연 SQL저장소까지 해당 엔티티를 관리하기 위한 모든 정보 제거
            //em.detach(member);

            //모든 엔티티를 준영속상태로 만들고 영속성 컨텍스트를 초기화한다.
            //em.clear();



            // commit하는 순간 데이터베이스에 insert sql을 보낸다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //엔티티 매니저 종료(영속성 컨텍스트가 관리하던 영속상태의 엔티티가 모두 준영속상태로 변경)
        }

        emf.close();    //엔티티 매니저 팩토리 종료
    }

}
