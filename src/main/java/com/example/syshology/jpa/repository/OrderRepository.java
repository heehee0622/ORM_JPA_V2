package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.entity.Order;
import com.example.syshology.jpa.type.OrderStatus;
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

}
