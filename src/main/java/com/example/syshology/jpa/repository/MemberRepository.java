package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.entity.Order;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQD{
    public Member findByName(String name);
    public List<Member> findMembersByName(String name);
//    @Query(value = "select m from Member m join m.orders")
    public List<Member> findJoinQD();
    public List<Order> findOrdersInMemberQD();
    public List<Member> findMembersByOrderByIdDesc();
    public List<Member> findMembersByOrderByNameDesc();
    public List<Member> findByIdBetween(Long startId, Long endId);
//    @Query(value = "SELECT m from Member m where m.id between :startId and :endId")
    public List<Member> findByIdBetweenQD(Long startId, Long endId);
//    @Query(value = "select m from Member m where m.id in (:idList)")
    public List<Member> findByIdInQD(List<Long> idList);
    public List<Member> findByIdGreaterThanAndNameContaining(Long id,String name);
//    @Query(value = "select m from Member m where m.id >:id and m.name like %:name")
    public List<Member> findByIdGreaterThanAndNameLikeQD(Long id, String name);
//    @Query(value = "select Max(m) from Member m group by m.name ")
    public List<Member> findByMaxNameGroupByBasicQD();
//    @Query(value = "select new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id)) from Member m group by m.name")
    public List<MemberDto> findByidSumGroupByNameQD();
//    @Query(value = "select  new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id))  from Member m group by m.name  having m.name like %:name%")
    public List<MemberDto> findByIdSumGroupBySumHavingQD(String name);
//    @Query(value = "select m from Member m join m.orders s on m.name = s.delivery.address.city")
    public List<Member> findByunRechableJoinQD();
    public QueryResults<Member> findByIdInPagingQD(List<Long> idList, int offset, int limit) ;
    public Page<Member> findByNameIn(List<String> nameList, Pageable pageable);
}
