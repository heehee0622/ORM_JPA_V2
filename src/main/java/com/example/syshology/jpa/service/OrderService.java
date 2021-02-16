package com.example.syshology.jpa.service;

import com.example.syshology.jpa.exception.NotEnoughStockException;
import com.example.syshology.jpa.entity.*;
import com.example.syshology.jpa.repository.ItemRepository;
import com.example.syshology.jpa.repository.MemberRepository;
import com.example.syshology.jpa.repository.OrderRepository;
import com.example.syshology.jpa.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 3:01
 * Project : IntelliJ IDEA
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) throws NotEnoughStockException {

        // 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new IllegalStateException("아이템이 존재 하지 않습니다."));

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery,"주문1", orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new IllegalStateException("주문이 존재 하지않습니다."));

        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        PageRequest pageRequest = PageRequest.of(0, 100);
        List<Order> orders = orderRepository.findAllByAllWithJoin(orderSearch.getMemberName(), orderSearch.getOrderStatus(), pageRequest);
        /**
         * OPEN IN VIEW 문제로 인한 테스트 코드
         */
        for (Order order : orders) {
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getCount();
                orderItem.getItem().getName();
                List<Category> categories = orderItem.getItem().getCategories();
                for (Category category : categories) {
                    category.getName();
                }
            }
        }
        return  orders;
    }
}
