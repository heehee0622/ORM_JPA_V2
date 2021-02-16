package com.example.syshology.jpa.dto;

import com.example.syshology.jpa.entity.Address;
import com.example.syshology.jpa.entity.Order;
import com.example.syshology.jpa.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SimpleOrderDto {
 private Long orderId;
 private String name;
 private LocalDateTime orderDate; //주문시간
 private OrderStatus orderStatus;
 private Address address;
 public SimpleOrderDto(Order order) {
 orderId = order.getId();
 name = order.getMember().getName();
 orderDate = order.getOrderDate();
 orderStatus = order.getStatus();
 address = order.getDelivery().getAddress();
 }
}