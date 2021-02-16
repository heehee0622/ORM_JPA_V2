package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.dto.OrderFlatDto;
import com.example.syshology.jpa.dto.OrderItemQueryDto;
import com.example.syshology.jpa.dto.OrderQueryDto;
import com.example.syshology.jpa.dto.SimpleOrderDto;
import com.example.syshology.jpa.entity.Order;
import com.example.syshology.jpa.type.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 2:56
 * Project : IntelliJ IDEA
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
      @Query(value = "select o from Order o join o.member m " +
              "where 1=1 and (:name is null or m.name like %:name%) and (:status is null or o.status = :status)")
      public List<Order> findAllByAllWithJoin(String name, OrderStatus status, Pageable pageable);
      @Query(value = "select o from Order o " +
              "join fetch  o.member m " +
              "join fetch o.delivery d")
      public List<Order> findAllWithMemberDelivery();
      @Query(value = "select o from Order o " +
              "join fetch  o.member m " +
              "join fetch o.delivery d", countQuery = "select count(o) from Order o ")
      public Page<Order> findAllWithMemberDeliveryPage(Pageable pageable);
      public Order findByName(String name);
      @Query(value = "select new com.example.syshology.jpa.dto.SimpleOrderDto(o.id, m.name, o.orderDate, o.status, d.address ) from Order o " +
              " join o.member m " +
              " join o.delivery d")
      public List<SimpleOrderDto> findOrderDtos();
      @Query(value = " select distinct o from Order o " +
              "join fetch o.member m " +
              "join fetch o.delivery d " +
              "join  fetch o.orderItems oi " +
              "join  fetch oi.item i")
      public List<Order> findAllWithItem();
      /**
       * 1:N 관계(컬렉션)를 제외한 나머지를 한번에 조회
       */
      @Query(value = "select new com.example.syshology.jpa.dto.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address ) from Order o " +
              " join o.member m " +
              " join o.delivery d")
      public List<OrderQueryDto> findOrders();

      @Query(value = " select  new com.example.syshology.jpa.dto.OrderItemQueryDto(oi.order.id,i.name, oi.orderPrice, oi.count ) from  OrderItem oi " +
              "join oi.item i " +
              "where oi.order.id = :orderId")
      public List<OrderItemQueryDto> findOrderItems(Long orderId);
      @Query(value = " select  new com.example.syshology.jpa.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) from OrderItem oi " +
              "join oi.item i " +
              "where oi.order.id in :orderIds")
      public List<OrderItemQueryDto> findOrderItemMap(List<Long> orderIds);
      @Query(value = "select new com.example.syshology.jpa.dto.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
              " from Order o join o.member m" +
              " join o.delivery d join o.orderItems oi " +
              " join oi.item i")
      public List<OrderFlatDto> findAllByTdo_flat();
}
