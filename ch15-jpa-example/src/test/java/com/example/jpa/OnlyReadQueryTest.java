package com.example.jpa;

import com.example.jpa.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * 읽기 전용 쿼리의 성능 최적화 테스트
 */
@SpringBootTest
public class OnlyReadQueryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void 스칼라_타입으로_조회() {
        em.createQuery("select o.id, o.orderDate, o.status from Order o", Order.class)
                .getResultList();
    }


    @Test
    void 읽기_전용_쿼리_힌트_사용() {
        TypedQuery<Order> query = em.createQuery("select o from Order o", Order.class);
        query.setHint("org.hibernate.readOnly", true);
    }

    //읽기 전용 트랜잭션 사용-트랜잭션 시작, 로직수행, 커밋의 과정은 이루어지나 영속성 컨텍스트를 플러시하지 않는다.
    // @Transactional(readOnly=true)


    //트랜잭션 밖에서 읽기(트랜잭션 사용안하기)
    // @Transactional(propagation=Propagation.NOT_SUPPORTED)
}


