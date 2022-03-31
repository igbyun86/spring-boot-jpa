package com.example.jpa;

import com.example.jpa.domain.Duck;
import com.example.jpa.domain.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ListenerTest {

    @PersistenceContext
    EntityManager em;


    @Test
    void 엔티티_리스너_test()  {
        Foo foo = new Foo();
        foo.setName("오리");

        em.persist(foo);
    }

    @Test
    void 별도의_리스너_test()  {
        Duck duck = new Duck();
        duck.setName("오리");

        em.persist(duck);
    }
}
