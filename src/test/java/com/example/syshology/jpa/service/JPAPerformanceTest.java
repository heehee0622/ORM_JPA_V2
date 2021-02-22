package com.example.syshology.jpa.service;

import com.example.syshology.jpa.dto.OrderQueryDto;
import com.example.syshology.jpa.dto.SimpleOrderDto;
import com.example.syshology.jpa.entity.Order;
import com.example.syshology.jpa.entity.OrderItem;
import org.assertj.core.api.Assertions;
import org.hibernate.mapping.ToOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-09
 * Time: 오후 3:10
 * Project : IntelliJ IDEA
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JPAPerformanceTest {
    @Autowired
    private PerformanceService performanceService;

    /*
     *     # JPA 활용 세미나 내용  #
     *     1. 지연로딩과 조회 성능 최적화
     *     2. 컬렉션 조회 최적화
     *     3. 페이징과 한계돌파
     */
    @Test
    public void jpaAutoQueryTest() {
        // 조인 테스트 하기
        /**
         1. 다대일 관계의 다 엔티티 조회 시 : 전체 조회시
         2.  Order 조회 : Order 조건: Member에 EARGER , Member 조건 : Order에 EAGER = 오더 조회 후 자동으로 멤버 로딩하는데 이 때 멤버의 오더를 조인으로 조회
         3.  Order 조회 : Order 조건: Member에 Lazy , Member 조건 : Order에 EAGER = 오더 조회는 단순 조회 쿼리 실행
         4.  Order 조회 : Order 조건: Member에 EAGER , Member 조건 : Order에  LAZY = 오더 조회는 단순 조회 쿼리 실행 후 Member n 번 조회.
         **/
//        List<Order> all = performanceService.ordersV1();
//        List<Member> members = performanceService.membersEntity();

        /**
         1.  다대일 관계의 다 엔티티 조회 시 : Name으로 조회 시
         2.  Order 조회 : Order 조건: Member에 EARGER , Member 조건 : Order에 EAGER = 오더 조회 후 자동으로 멤버 로딩하는데 이 때 멤버의 오더를 조인으로 조회
         3.  Order 조회 : Order 조건: Member에 Lazy , Member 조건 : Order에 EAGER = 오더 조회는 단순 조회 쿼리 실행
         4.  Order 조회 : Order 조건: Member에 EAGER , Member 조건 : Order에  LAZY = 오더 조회는 단순 조회 쿼리 실행 후 Member n 번 조회.
         **/
//        performanceService.findOrderName("주문1");

        /**
         1. 다대일 관계의 다 엔티티 조회 시 : ID로 조회 시
         2. Order 조회 시 :  Order의 Member에 EAGER :  Member의 Order에 EAGER :  오더 조인 조건 조회 후 Member 로딩 되면서 Order 자동 조회
         3. Order 조회 시 : Order의 Member에 EAGER :  Member의 Order에 Lazy : 오더 조회 시 조인 조건으로 조회
         4. Order 조회 시  : Order에 Member LAZY :  Member의 Order에 EAGER :  오더 단순 조회 쿼리
         **/
        performanceService.findOrderId(4L);

        /**
         1. 다대일 관계의 1 엔티티 조회 시 : ID 로 조회 시
         2. Member 조회 시 : Order의  Memberd eager: Member의 Order에 Eager : 조인 조건으로 조회
         3. Member 조회 시 : Order의  Memberd lazy: Member의 Order에 Eager : 조인 조건으로 조회
         4. Member 조회 시 : Order의  Memberd eager: Member의 Order에 lazy :  단순 조건으로 조회
         */
//        performanceService.findMemberId(8L);

        /*
         1. 다대일 관계의 1 엔티티 조회 시 : name으로 조회
         1. Member 조회 시 : Order의  Memberd eager, Member의 Order에 Eager : 단순 조건으로 Member 조회 후  Order 쿼리 실행 한다.
         2. Member 조회 시 :  Order의  Memberd lazy, Member의 Order에 Eager : 단순 조건으로 Member 조회 후 Order 쿼리 실행 한다.
         3. Member 조회 시  :  Order의  Memberd eager, Member의 Order에 lazy : 단순 조건으로 조회
         */
//          performanceService.findMemberName("userA");
    }

    @Test
    public void ordersV1() {
        /**
         *    - 엔티티를 컨트롤러 레벨에 그대로 노출 시 문제 발생
         *      => 엔티티가 노출 됨으로 해서 클라이언트와 시스템 설계와 의존 관계 생김
         *      => Json 전송 중 오버 플로우 에러가 날 수 있다. JsonIgnore 사용 한다. (양방향 연관관계일 경우 생김)
         *      => 컨트롤러에서 객체가 프록시 일 경우 json 변경 시 에러가 생김. (프록시를 json올 변경 안하게 하는 방법이 있다. 하지만 엔티티를 직접 노출 안하면 문제가 해결 된다.)
         *      DTO를 Response 전송 해야 한다.
         */
        List<Order> all = performanceService.findOrdersAll();
//        List<Member> members = performanceService.membersEntity();

    }


    @Test
    public void ordersV2() {
        /**
         * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
         * - 단점: 지연로딩으로 쿼리 N번 호출
         */
        List<SimpleOrderDto> orders = performanceService.ordersV2();
    }


    @Test
    public void ordersV3() {
        /**
         * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
         * - fetch join으로 쿼리 1번 호출
         */
        List<SimpleOrderDto> orders = performanceService.ordersV3();
    }
    @Test
    public void  ordersV4() {
        /**
         * V4. JPA에서 DTO로 바로 조회
         * - 쿼리 1번 호출
         * - select 절에서 원하는 데이터만 선택해서 조회
         */
        performanceService.findOrderDtos();
    }

    /*
       여기서 부터는 컬렉션 조회 죄적화  입니다.
     */
    @Test
    public void ordersCollectonV1() {
        /**
         * 엔티티 직접 노출 하므로 좋은 방법 X
         */
        List<Order> all = performanceService.findOrdersAll();
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName()); //Lazy 강제 초기화
        }
    }
    @Test
    public void ordersCollectionV2() {
        /**
         * DTO로 변환 하여 엔티티를 외부에서 볼수 없게 숨긴다.
         */
        List<Order> orders = performanceService.findOrdersAll();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
    }
    @Test
    public void ordersCollectionV3() {
        /**
         - 페치 조인으로 SQL이 1번만 실행됨
         - distinct 를 사용한 이유는 1대다 조인이 있으므로 데이터베이스 row가 증가한다. 그 결과 같은 order
         엔티티의 조회 수도 증가하게 된다. JPA의 distinct는 SQL에 distinct를 추가하고, 더해서 같은 엔티티가
         조회되면, 애플리케이션에서 중복을 걸러준다. 이 예에서 order가 컬렉션 페치 조인 때문에 중복 조회 되는
         것을 막아준다.
        - 단점
         페이징 불가능
         */
        List<Order> orders = performanceService.findAllWithItem();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
    }
    @Test
    public void  orderV3_page(){
        Page<Order> allWithMemberDelivery = performanceService.findAllWithMemberDeliveryPage(0, 100);
        List<SimpleOrderDto> collect = allWithMemberDelivery.stream().map(o -> new SimpleOrderDto(o)).collect(toList());
        /**
         * 페치 조인 쿼리 후에 Member가 로딩 되면서 멤버 안의 Order 리스트가  N번 조회 된다.
         *spring:
         *  jpa:
         *  properties:
         *  hibernate:
         *  default_batch_fetch_size: 1000
         *
         *  order 조회 쿼리는 한번으로 줄어든다. (in 쿼리로 생성 된다.)
         *
         */
    }
    @Test
    public void  orderV4_dto(){
        /**
         * 컬렉션은 별도로 조회
         * Query: 루트 1번, 컬렉션 N 번
         * 단건 조회에서 많이 사용하는 방식
         */
        /*Query: 루트 1번, 컬렉션 N 번 실행
        ToOne(N:1, 1:1) 관계들을 먼저 조회하고, ToMany(1:N) 관계는 각각 별도로 처리한다.
        이런 방식을 선택한 이유는 다음과 같다.
        ToOne 관계는 조인해도 데이터 row 수가 증가하지 않는다.
        ToMany(1:N) 관계는 조인하면 row 수가 증가한다.
                row 수가 증가하지 않는 ToOne 관계는 조인으로 최적화 하기 쉬우므로 한번에 조회하고, ToMany 관계
        는 최적화 하기 어려우므로 findOrderItems() 같은 별도의 메서드로 조회한다*/
        List<OrderQueryDto> orderQueryDtos = performanceService.findOrderQueryDtos();
    }
    @Test
    public void  orderV5(){
        /**
         * 최적화
         * Query: 루트 1번, 컬렉션 1번
         * 데이터를 한꺼번에 처리할 때 많이 사용하는 방식
         *
         */
        List<OrderQueryDto> orderQueryDtos = performanceService.findAllByDto_optimization();
    }
    @Test
    public void  orderV6(){
/*
        Query: 1번
                단점
        쿼리는 한번이지만 조인으로 인해 DB에서 애플리케이션에 전달하는 데이터에 중복 데이터가 추가되
        므로 상황에 따라 V5 보다 더 느릴 수 도 있다.
        애플리케이션에서 추가 작업이 크다.
        페이징 불가능
*/
        List<OrderQueryDto> orderQueryDtos = performanceService.findAllByDto_flat();
    }
}
