package com.example.jpa;

import com.example.jpa.entity.cascade.Child;
import com.example.jpa.entity.cascade.Parent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
public class CascadeTest {

    @Test
    void 부모_자식_등록_test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        child1.setParent(parent);       //연관관계 추가
        child2.setParent(parent);       //연관관계 추가

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent); //부모만 영속화하면 CascadeType.PERSIST로 설정한 자식 엔티티까지 함께 영속화해서 저장


        //em.remove(parent);  //부모 엔티티만 삭제하면 연관된 자식 엔티티도 함께 삭제된다.

        tran.commit();
        em.close();
    }
}
