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
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public void save(Order order) {
//        entityManager.persist(order);
//    }
//    public void remove(Order order){
//        entityManager.remove(order);
//    }
//
//    public Optional<Order> findOne(Long id) {
//        Order order = entityManager.find(Order.class, id);
//        return Optional.ofNullable(order);
//    }
//
//    public List<Order> findAll(OrderSearch orderSearch) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
//        Root<Order> o = cq.from(Order.class);
//        List<Predicate> criteria = new ArrayList<Predicate>();
//        // 주문상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
//            criteria.add(status);
//        }
//        // 회원이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            //회원과 조인
//            Join<Order, Member> m = o.join("member", JoinType.INNER);
//            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
//            criteria.add(name);
//        }
//        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
//        TypedQuery<Order> query = entityManager.createQuery(cq).setMaxResults(1000);//최대 1000건
//        return query.getResultList();
//    }
      @Query(value = "select o from Order o join o.member m " +
              "where 1=1 and (:name is null or m.name like %:name%) and (:status is null or o.status = :status)")
      public List<Order> findAllByAllWithJoin(String name, OrderStatus status, Pageable pageable);

}
