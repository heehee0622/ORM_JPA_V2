package com.example.syshology.jpa.service;

import com.example.syshology.jpa.entity.Item;
import com.example.syshology.jpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 2:44
 * Project : IntelliJ IDEA
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    public List<Item> findIdItems(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        Item item = itemRepository.findById(itemId).get();
        return item;
    }
    @Transactional
    public void updateItem(Long id, String name, int price) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalStateException("아이템이 존재 하지 않아요"));
        item.setName(name);
        item.setPrice(price);
    }
}
