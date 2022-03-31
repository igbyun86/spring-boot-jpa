package com.example.jpa;

import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class EntityGraphTest {
    
    @PersistenceContext
    EntityManager em;
    
    
    @Test
    void 동적_엔티티_그래프() {
        EntityGraph<Order> graph = em.createEntityGraph(Order.class);
        graph.addAttributeNodes("member");
        
        Map hints = new HashMap();
        hints.put("javax.persistence.fetchgraph", graph);

        Order order = em.find(Order.class, 1L, hints);
    }


    @Test
    void 동적_엔티티_subgraph_그래프() {
        EntityGraph<Order> graph = em.createEntityGraph(Order.class);
        graph.addAttributeNodes("member");
        Subgraph<OrderItem> orderItems = graph.addSubgraph("orderItems");
        orderItems.addAttributeNodes("item");

        Map hints = new HashMap();
        hints.put("javax.persistence.fetchgraph", graph);

        Order order = em.find(Order.class, 1L, hints);
    }
}
