package com.example.jpa.querydsl;

import com.example.jpa.EntityManagerFactoryHelper;
import com.example.jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@SpringBootTest
public class BulkTest {

    @Test
    @DisplayName("bulk update")
    void test() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String sql = "" +
                "update Product p " +
                "set p.price = p.price * 1.1 " +
                "where p.stockAmount < :stockAmount";

        int resultCount = em.createQuery(sql)
                .setParameter("stockAmount", 10)
                .executeUpdate();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("bulk delete")
    void test2() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String sql = "" +
                "delete from Product p " +
                "where p.stockAmount < :stockAmount";

        int resultCount = em.createQuery(sql)
                .setParameter("stockAmount", 10)
                .executeUpdate();

        tran.commit();
        em.close();
    }

    @Test
    @DisplayName("bulk insert")
    void test3() {
        EntityManagerFactory emf = EntityManagerFactoryHelper.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        String sql =
                "insert into ProductTemp(id, name, price, stockAmount) " +
                "select p.id, p.name, p.price, p.stockAmount from Product p " +
                "where p.price < :price";

        int resultCount = em.createQuery(sql)
                .setParameter("price", 10)
                .executeUpdate();

        tran.commit();
        em.close();
    }

}
