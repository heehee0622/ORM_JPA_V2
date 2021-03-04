package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.entity.Order;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-22
 * Time: 오후 5:33
 * Project : IntelliJ IDEA
 */
public interface MemberRepositoryQD {
    public List<Member> findOneQD();
    public List<Member> findJoinQD() ;
    public List<Member> findOneJoinFetchQD();
    public List<Member> findMembersByOrderByQD(Sort sort);
    public List<Order> findOrdersInMemberQD();
    public List<Member> findByIdBetweenQD(Long startId, Long endId);
    public List<Member> findByIdInQD(List<Long> idList);
    public List<Member> findByIdGreaterThanAndNameLikeQD(Long id, String name);
    public List<Member> findByMaxNameGroupByBasicQD();
//    @Query(value = "select new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id)) from Member m group by m.name ")
    public List<MemberDto> findByidSumGroupByNameQD();
//    @Query(value = "select  new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id))  from Member m group by m.name  having m.name like %:name%")
    public List<MemberDto> findByIdSumGroupBySumHavingQD(String name);
//    @Query(value = "select m from Member m join m.orders s on m.name = s.delivery.address.city")
    public List<Member> findByunRechableJoinQD();
    public QueryResults<Member> findByIdInPagingQD(List<Long> idList, int offset, int limit) ;

}
