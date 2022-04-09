package com.example.jpa.entity;

import com.example.jpa.entity.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;     //주문 가격
    private int count;          //주문 수량

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public void printItem() {
        System.out.println("TITLE=" + item.getTitle());
    }
}
