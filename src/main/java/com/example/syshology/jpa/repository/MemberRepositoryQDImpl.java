package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.entity.QMember;
import com.example.syshology.jpa.entity.QOrder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-22
 * Time: 오후 5:36
 * Project : IntelliJ IDEA
 */

@RequiredArgsConstructor
public class MemberRepositoryQDImpl implements MemberRepositoryQD {
    protected final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findOneQD() {
        QMember member = QMember.member;
        return jpaQueryFactory.selectFrom(member).fetch();
    }

    @Override
    public List<Member> findOneJoinFetchQD() {
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        return jpaQueryFactory.selectFrom(member).join(member.orders, order).fetchJoin().fetch();
    }

    @Override
    public List<Member> findJoinQD() {
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        return jpaQueryFactory.selectFrom(member ).join(member.orders, order).fetch();
    }

    @Override
    public List<Member> findMembersByOrderByQD(Sort sort) {
        QMember member = QMember.member;
        JPAQuery<Member> memberJPAQuery = jpaQueryFactory.selectFrom(member);
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Member.class, prop);
            memberJPAQuery.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        return memberJPAQuery.fetch();
    }

    @Override
    public List<com.example.syshology.jpa.entity.Order> findOrdersInMemberQD() {
        QMember member = QMember.member;
        return jpaQueryFactory.select(Projections.fields(com.example.syshology.jpa.entity.Order.class, member.orders)).from(member).fetch();
    }

    @Override
    public List<Member> findByIdBetweenQD(Long startId, Long endId) {
        QMember member = QMember.member;
        return jpaQueryFactory.select(member).from(member).where(member.id.between(startId, endId)).fetch();
    }

    @Override
    public List<Member> findByIdInQD(List<Long> idList) {
        QMember member = QMember.member;
        return jpaQueryFactory.select(member).from(member).where(member.id.in(idList)).fetch();
    }

    @Override
    public List<Member> findByIdGreaterThanAndNameLikeQD(Long id, String name) {
        QMember member = QMember.member;
        return jpaQueryFactory.select(member).from(member).where(member.id.gt(id).and(member.name.like("%"+name))).fetch();
    }

    @Override
//    @Query(value = "select Max(m) from Member m group by m.name ")
    public List<Member> findByMaxNameGroupByBasicQD() {
        QMember member = QMember.member;
        return jpaQueryFactory.select(Projections.fields(Member.class, member.name, member.id.count().as("id"))).from(member).groupBy(member.name).fetch();
    }

    @Override
//    @Query(value = "select new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id)) from Member m group by m.name ")
    public List<MemberDto> findByidSumGroupByNameQD() {
        QMember member = QMember.member;
        return jpaQueryFactory.select(Projections.constructor(MemberDto.class, member.name, member.id.sum().as("count"))).from(member).groupBy(member.name).fetch();
    }

    @Override
    //    @Query(value = "select  new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id))  from Member m group by m.name  having m.name like %:name%")
    public List<MemberDto> findByIdSumGroupBySumHavingQD(String name) {
        QMember member = QMember.member;
        return jpaQueryFactory.select(Projections.constructor(MemberDto.class, member.name, member.id.sum().as("count"))).from(member).groupBy(member.name).having(member.name.like("%"+name+"%")).fetch();
    }

    @Override
//    @Query(value = "select m from Member m join m.orders s on m.name = s.delivery.address.city")
    public List<Member> findByunRechableJoinQD() {
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        return jpaQueryFactory.select(member).from(member).join(order).on(order.name.eq(member.name)).fetch();
    }

    @Override
    public QueryResults<Member> findByIdInPagingQD(List<Long> idList, int offset, int limit) {
        QMember member = QMember.member;
        return jpaQueryFactory.select(member).from(member).where(member.id.in(idList)).offset(offset).limit(limit).fetchResults();
    }


}
