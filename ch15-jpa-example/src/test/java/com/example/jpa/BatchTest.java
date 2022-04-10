package com.example.jpa;

import com.example.jpa.entity.Product;
import org.hibernate.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
public class BatchTest {

    @PersistenceContext
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;


    @Test
    void 페이징_배치_처리() {
        int pageSize = 100;
        for (int i = 0; i < 10; i++) {
            List<Product> resultList = em.createQuery("select p from Product p", Product.class)
                    .setFirstResult(i * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();

            //비즈니스 로직 실행
            resultList.forEach(p -> {
                p.setPrice(p.getPrice() + 100);
            });

            em.flush();
            em.clear();
        }

        em.close();
    }


    @Test
    @DisplayName("하이버네이트 scroll을 사용하여 JDBC커서 사용")
    void 하이버네이트_scroll() {
        Session session = em.unwrap(Session.class);

        ScrollableResults scroll = session.createQuery("select p from Product p")
                .setCacheMode(CacheMode.IGNORE)     //2차 캐시기능을 끈다
                .scroll(ScrollMode.FORWARD_ONLY);

        int count = 0;

        while (scroll.next()) {
            Product p = (Product) scroll.get(0);
            p.setPrice(p.getPrice() + 100);

            count++;
            if (count % 100 == 0) {
                session.flush();
                session.clear();
            }

            session.close();
        }
    }


    @Test
    void 하이버네이트_무상태_세션() {
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        StatelessSession session = sessionFactory.openStatelessSession();
        Transaction tx = session.beginTransaction();
        ScrollableResults scroll = session.createQuery("select p from Product p").scroll();

        while (scroll.next()) {
            Product p = (Product) scroll.get(0);
            p.setPrice(p.getPrice() + 100);
            session.update(p);      //직접 update를 호출해야 한다.
        }

        tx.commit();
        session.close();
    }






}
