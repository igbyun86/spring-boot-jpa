package com.springboot.jpa.service;

import com.springboot.jpa.domain.item.Item;
import com.springboot.jpa.domain.item.QItem;
import com.springboot.jpa.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findById(id).orElse(new Item());
    }

    /**
     * QueryDSL 조회
     * @param name
     * @param price
     * @return
     */
    public List<Item> findItemByNameAndPrice(String name, int price) {
        QItem item = QItem.item;

        itemRepository.findAll(item.name.eq(name).and(item.price.gt(price)));
        return null;
    }

}
