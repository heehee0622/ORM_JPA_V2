package com.example.syshology.jpa.service;

import com.example.syshology.jpa.dto.OrderFlatDto;
import com.example.syshology.jpa.dto.OrderItemQueryDto;
import com.example.syshology.jpa.dto.OrderQueryDto;
import com.example.syshology.jpa.dto.SimpleOrderDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.entity.Order;
import com.example.syshology.jpa.repository.MemberRepository;
import com.example.syshology.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-09
 * Time: 오후 4:11
 * Project : IntelliJ IDEA
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformanceService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    public List<Order> findOrdersAll() {
        /**
         *  https://siyoon210.tistory.com/57
         * JPA 조인(JOIN) 전략
         * 엔티티(Entity)들 간의 관계가 맺어져 있다면, JPA는 조인(JOIN)을 이용해서 데이터베이스를 조회하게 됩니다.
         *  조인전략중에 성능이 가장 좋은 조인 전략은 내부조인(INNER JOIN)입니다. 하지만 연관된 외래키가 null이 존재하는 경우에 내부조인을
         *  사용할 경우 일부만 필터링을 해서 조회하는 문제가 생길 수 있습니다. 이러한 이유로 JPA는 외부조인(OUTER JOIN)을 기본설정으로 수행하게 됩니다.
         *
         *  밑의 Order에는 ManyToOne 조인은 Left가 기본적으로 붙는다. 하지만 nullable, optional=false를 하면 inner 조인이 되어야 하는데 되지 않는다. 확인 필요.
         */
        List<Order> all = orderRepository.findAll();
//        for (Order order : all) {
//            order.getMember().getName(); //Lazy 강제 초기화
//            order.getDelivery().getAddress(); //Lazy 강제 초기환
//        }
        return all;
    }
    public List<Member> membersEntity(){
        List<Member> members = memberRepository.findAll();
        return members;
    }
    public Order findOrderName(String name  ){
        return orderRepository.findByName(name);
    }
    public Optional<Order> findOrderId(Long id){
        return orderRepository.findById(id);
    }
    public Member findMemberName(String name){
        return memberRepository.findByName(name);
    }
    public Optional<Member> findMemberId(Long id){
        return memberRepository.findById(id);
    }
    public List<SimpleOrderDto> ordersV2() {
        List<Order> all = orderRepository.findAll();
        List<SimpleOrderDto> result = all.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }
    public List<SimpleOrderDto> findOrderDtos() {
        return orderRepository.findOrderDtos();
    }
    public List<Order> findAllWithItem() {
        return orderRepository.findAllWithItem();
    }
    public Page<Order> findAllWithMemberDeliveryPage(Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return orderRepository.findAllWithMemberDeliveryPage(pageRequest);
    }
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orderDtoList = orderRepository.findOrders();
        orderDtoList.forEach(o-> {
            List<OrderItemQueryDto> orderItems = orderRepository.findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return orderDtoList;
    }
    public List<OrderQueryDto> findAllByDto_optimization(){
        List<OrderQueryDto> orders = orderRepository.findOrders();
        List<OrderItemQueryDto> orderItemQueryDtos = orderRepository.findOrderItemMap(toOrderIds(orders));
        Map<Long, List<OrderItemQueryDto>> collect = orderItemQueryDtos.stream()
                .collect(groupingBy(OrderItemQueryDto::getOrderId));
        orders.forEach(o -> o.setOrderItems(collect.get(o.getOrderId())));
        return orders;
    }
    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }
   public List<OrderQueryDto> findAllByDto_flat(){
       List<OrderFlatDto> flats = orderRepository.findAllByTdo_flat();
       return flats.stream()
               .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                               o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                       mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                               o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
               )).entrySet().stream()
               .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                       e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                       e.getKey().getAddress() ))
               .collect(toList());
   }
}
