package com.springboot.jpa.service;

import com.springboot.jpa.domain.item.Item;
import com.springboot.jpa.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void 상품등록() {
        //Given
        Item item = new Item();
        item.setName("JPA 프로그래밍");

        //When
        itemService.saveItem(item);

        //Then
        Assertions.assertEquals(item, itemRepository.findById(item.getId()));
    }


}
