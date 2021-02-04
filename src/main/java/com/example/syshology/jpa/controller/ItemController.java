package com.example.syshology.jpa.controller;

import com.example.syshology.jpa.entity.Book;
import com.example.syshology.jpa.entity.Item;
import com.example.syshology.jpa.model.BookForm;
import com.example.syshology.jpa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-27
 * Time: 오후 2:13
 * Project : IntelliJ IDEA
 */
@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping(value = "/items/new")
    public ModelAndView createFrom(ModelAndView modelAndView) {
        modelAndView.addObject("form", new BookForm());
        modelAndView.setViewName("items/createItemForm");
        return modelAndView;
    }

    @PostMapping(value = "/items/new")
    public void create(Book form, HttpServletResponse response) throws IOException {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        response.sendRedirect("/items");
    }
    @GetMapping(value = "/items")
    public ModelAndView list(ModelAndView model){
        List<Item> idItems = itemService.findIdItems();
        model.addObject("items",idItems);
        model.setViewName("items/itemList");
        return model;
    }
    @GetMapping(value = "/items/{itemId}/edit")
    public ModelAndView updateItemForm(@PathVariable("itemId") Long itemId, ModelAndView model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        model.addObject("item",item);
        model.setViewName("items/updateItemForm");
        return model;
    }
    @PostMapping(value = "/items/{itemId}/edit")
    public void updateItem(@ModelAttribute("item") Book form, HttpServletResponse response) throws IOException {
        itemService.updateItem(form.getId(), form.getName(), form.getPrice());
        response.sendRedirect("/items");
    }
}
