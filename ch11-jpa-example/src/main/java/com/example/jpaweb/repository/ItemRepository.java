package com.example.jpaweb.repository;

import com.example.jpaweb.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {


    @PersistenceContext
    EntityManager em;

    public void save(Item item) {
        if (item.getId() != null) {
            em.merge(item);
        } else {
            em.persist(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
