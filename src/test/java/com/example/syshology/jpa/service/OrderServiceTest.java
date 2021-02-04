package com.example.syshology.jpa.service;

import com.example.syshology.jpa.exception.NotEnoughStockException;
import com.example.syshology.jpa.entity.*;
import com.example.syshology.jpa.repository.OrderRepository;
import com.example.syshology.jpa.type.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 3:47
 * Project : IntelliJ IDEA
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품준문() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 1000, 10);//이름 가격 재고
        int orderCount = 2;
        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("주문정보가 존재 하지 않급니다."));

        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다.", 1000 * 2, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골JPA", 10000, 10); // 이름, 가격, 재고
        int orderCount = 11; // 재고보다 많은 수량
        //When
        orderService.order(member.getId(), item.getId(), orderCount);
        //Then
        Assert.fail("재고 수량 부족 예외가 발생해야 합니다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골JPA", 10000, 10); //이름 가격 재고
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);
        //When
        Order getOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("주문 정보가 존재 하지 않습니다."));
        //Then
        Assert.assertEquals("주문 취소시 상태는 Cancle 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = Member.builder().name("회원1").orders(new ArrayList<Order>()).address(new Address("서울", "강가", "123-123")).build();
        entityManager.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder().name(name).price(price).stockQuantity(stockQuantity).build();
        entityManager.persist(book);
        return book;
    }
    @Test
    public void 모두조회(){
        orderRepository.findById(1L);// ONE TO MANY EAGER 전략 으로 조회시 N+1
    }

}
