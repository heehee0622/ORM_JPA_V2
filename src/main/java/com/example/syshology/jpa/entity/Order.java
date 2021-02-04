package com.example.syshology.jpa.entity;

import com.example.syshology.jpa.type.DeliveryStatus;
import com.example.syshology.jpa.type.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
 @Id @GeneratedValue
 @Column(name = "order_id")
 private Long id;
 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "member_id")
 private Member member; //주문 회원
 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
 private List<OrderItem> orderItems = new ArrayList<>();
 @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
 @JoinColumn(name = "delivery_id")
 private Delivery delivery; //배송정보
 private LocalDateTime orderDate; //주문시간
 @Enumerated(EnumType.STRING)
 private OrderStatus status; //주문상태 [ORDER, CANCEL]
 //==연관관계 메서드==//
 public void setMember(Member member) {
 this.member = member;
 List<Order> orderList = member.getOrders();
 member.getOrders().add(this);
 }
 public void addOrderItem(OrderItem orderItem) {
 orderItems.add(orderItem);
 orderItem.setOrder(this);
 }
 public void setDelivery(Delivery delivery) {
 this.delivery = delivery;
 delivery.setOrder(this);
 }
 //== 생성 메서드 ==//
 public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
  Order order = new Order();

  try {
  order.setMember(member);
  order.setDelivery(delivery);
  }catch (Exception e){
   System.out.println(e.getMessage());
  }
  for(OrderItem orderItem : orderItems) {
   order.addOrderItem(orderItem);
  }
  order.setStatus(OrderStatus.ORDER);
  order.setOrderDate(LocalDateTime.now());
  return order;
 }

 //== 비즈니스 로직 ==//
 /**
  * 주문 취소
  */
 public void cancel() {
  if(delivery.getStatus() == DeliveryStatus.COMP) {
   throw new RuntimeException("이미 배송 완료된 상품은 취소가 불가능합니다.");
  }
  this.setStatus(OrderStatus.CANCEL);
  for(OrderItem orderItem : orderItems) {
   orderItem.cancel();
  }
 }

 //== 조회 로직 ==//
 /**
  * 전체 주문 가격 조회
  */
 public int getTotalPrice() {
        /*
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
         */
  return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
 }
}