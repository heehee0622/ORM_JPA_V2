package com.example.syshology.jpa.controller;

import com.example.syshology.jpa.entity.Item;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.entity.Order;
import com.example.syshology.jpa.repository.OrderRepository;
import com.example.syshology.jpa.repository.OrderSearch;
import com.example.syshology.jpa.service.ItemService;
import com.example.syshology.jpa.service.MemberService;
import com.example.syshology.jpa.service.OrderService;
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
 * Time: 오후 4:04
 * Project : IntelliJ IDEA
 */
@RestController
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private MemberService memberService;
    @Autowired private ItemService itemService;
    @Autowired private OrderRepository orderRepository;
    @GetMapping(value = "/order")
    public ModelAndView createForm(ModelAndView modelAndView){
        List<Member> members = memberService.findMembers();
        List<Item> idItems = itemService.findIdItems();
        modelAndView.addObject("members", members);
        modelAndView.addObject("items",idItems);
        modelAndView.setViewName("order/orderForm");
        return modelAndView;
    }

    @PostMapping(value = "/order")
    public void order(HttpServletResponse response, @RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) throws Exception {
        orderService.order(memberId,itemId,count);
        response.sendRedirect("/orders");
    }
    @GetMapping(value = "/orders")
    public ModelAndView orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, ModelAndView model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addObject("orders", orders);
        model.setViewName("order/orders");
        return model;
    }
    @PostMapping(value = "/orders/{orderId}/cancel")
    public void cancelOrder(HttpServletResponse response, @PathVariable("orderId") Long orderId) throws IOException {
        orderService.cancelOrder(orderId);
        response.sendRedirect("/orders");
    }

}
