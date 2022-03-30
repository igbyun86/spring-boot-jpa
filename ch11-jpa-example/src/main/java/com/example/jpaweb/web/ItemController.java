package com.example.jpaweb.web;

import com.example.jpaweb.domain.item.Book;
import com.example.jpaweb.domain.item.Item;
import com.example.jpaweb.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    /**
     * 상품 등록 페이지
     * @return
     */
    @GetMapping("/items/new")
    public String createForm() {
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@ModelAttribute Book item) {

        itemService.saveItem(item);
        return "redirect:/items";
    }

    /**
     * 상품 목록 페이지
     * @param model
     * @return
     */
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }


    /**
     * 상품 수정 페이지
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);
        return "items/updateItemForm";
    }

    /**
     * 상품 수정
     * @param item
     * @return
     */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("item") Book item) {

        itemService.saveItem(item);
        return "redirect:/items";
    }


}
