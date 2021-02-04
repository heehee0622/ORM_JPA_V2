package com.example.syshology.jpa.service;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.dto.MemberIdDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.projection.MemberProjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-01
 * Time: 오후 4:43
 * Project : IntelliJ IDEA
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JPAQueryTest {
    @Autowired
    MemberService memberService;

    //  ============================================ 기본 조회 테스트 ==========================================
    @Test
    public void findMember() {
        /*
        1.  id  조회 이기 때문에  연관관계를 확인 하여 조인하고, 연관관계는 fetch 기본 전략을 따른다. 조인으로 조회 한다.
         */
        Member member = memberService.findOne(1L);
        System.out.println(member.getName());
    }

    @Test
    public void findByName() {
        /*
            1. id가 아닌 name 으로 조회 하기 때문에 엔티티 연관관계를 보고 조인하지 않는다.
            2. 멤버를 조회 후 로딩 된 엔티티에서 연관관계의 fetch 전략을 보고 즉시로딩이면 조회 한다.
         */
        List<Member> members = memberService.findByName("userA");
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findMembers()/*
             1. id 조건으로 조회 하지 않기 때문에 엔티티 연관관계를 보고 조인하지 않는다.
            2. 멤버를 조회 후 로딩 된 엔티티에서 연관관계의 fetch 전략을 보고 즉시로딩이면 조회 한다.
         */ {
        List<Member> members = memberService.findMembers();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findMembersWithJpql() {
///        1. id 조건으로 조회 하지 않기 때문에 엔티티 연관관계를 보고 조인하지 않는다.
//        2. 멤버를 조회 후 로딩 된 엔티티에서 연관관계의 fetch 전략을 보고 즉시로딩이면 조회 한다.
        List<Member> members = memberService.findByJpql();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findMemberWithJpqlJoin() {
        // 1. JPQL 사용시 기본 엔티티 연관관계를 보고 조인하지 않는다.
        // 2. 멤버를 조회 후 로딩 된 엔티티에서 연관관계의 fetch 전략을 보고 즉시로딩이면 조회 한다.
        List<Member> byIdJoinJpql = memberService.findByIdJoinJpql();
        for (Member member : byIdJoinJpql) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findMemberWithJpqlJoinFetch() {
        // 1. JPQL 사용시 기본 엔티티 연관관계를 보고 조인하지 않는다. 페치 조인은 타겟 대상도 한 쿼리로 로딩 한다.
        // 2. fetch 조인을 사용 하여서 N+1 쿼리를 하나의 쿼리로 바꿀 수 있다.
        List<Member> byIdJoinJpql = memberService.findByIdJoinFetchJpql();
        for (Member member : byIdJoinJpql) {
            System.out.println(member.getName());
        }
    }

    // ========================================== ORDER BY ===================================================
    @Test
    public void findMemberOrderByIdMethod() {
        memberService.findOrderByIdDesc();
    }

    @Test
    public void findMemberOrderByNameMethod() {
        memberService.findOrderByNameDesc();
    }

    @Test
    public void findMemberOrderByJpql() {
        memberService.findOrderByJpql();
    }

    @Test
    public void findAllOrderBy() {
        memberService.findMembersWithSort();
    }

    //    ===================================== WHERE, BETWEEN, IN, 비교문, like ====================================
    @Test
    public void findWhereBetween() {
        List<Member> members = memberService.findWhereBtween();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findWhereBetweenJpql() {
        List<Member> members = memberService.findWhereBetweenJpql();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findWhereIn() {
        List<Long> longList = Arrays.asList(1L, 9L);
        List<Member> members = memberService.findWhereIn(longList);
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findWhereInJpql() {
        List<Member> members = memberService.findWhereInJpql(Arrays.asList(8L, 10L));
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findWhereCompare() {
        List<Member> members = memberService.findWhereCompare();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findWhereCompareJpql() {
        List<Member> members = memberService.findWhereCompareJpql();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    //    ===================================== GROUP BY HAVING ==============================================
    @Test
    public void groupByBasic() {
        List<Member> byGroupByBasic = memberService.findByGroupByBasic();
        for (Member member : byGroupByBasic) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void groupByBasicSum() {
        /**
         * 쿼리에 DTO를 매핑 해서 값을 조회 했기 때문에 필요한 컬럼만 select 에 포함 된다.
         */
        List<MemberDto> byGroupByBasic = memberService.findByGroupBySum();
        for (MemberDto member : byGroupByBasic) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void groupByBasicSumHaving() {
        /**
         * 쿼리에 DTO를 매핑 해서 값을 조회 했기 때문에 필요한 컬럼만 select 에 포함 된다.
         */
        List<MemberDto> byGroupByBasic = memberService.findByGroupBySumHaving();
        for (MemberDto member : byGroupByBasic) {
            System.out.println(member.getName());
        }
    }

    //    ===================================== INTERFACE BASED Projection ===================================================
    @Test
    public void findByOrderByAsc() {
        List<MemberProjection> orderByNameAsc = memberService.findOrderByNameAsc();
        for (MemberProjection memberProjection : orderByNameAsc) {
            System.out.println(memberProjection.getName());
        }
    }

    @Test
    public void findByOrderByAscJpql() {
        List<MemberProjection> orderByNameAsc = memberService.findOrderByNameAscJpql();
        for (MemberProjection memberProjection : orderByNameAsc) {
            System.out.println(memberProjection.getValues());
        }
    }
//    ===================================== DTO BASED PROJECTION ==========================================================
    //   @Query(value = "select  new com.example.syshology.jpa.dto.MemberIdDto(m.name, m.id ) from Member m where m.name like %:name%")
    //  위의 쿼리랑 DTO의 필드 이름이랑 같아야 한다. m.name -> 피드이름 name이어야 한다.
    @Test
    public void findByNameLike(){
        List<MemberIdDto> list = memberService.findByNameLike();
        for (MemberIdDto memberDto : list) {
            System.out.println(memberDto.getName());
        }
    }

    @Test
    public void findByNameLikeJpql(){
        List<MemberIdDto> list = memberService.findByNameLikeJpql();
        for (MemberIdDto memberDto : list) {
            System.out.println(memberDto.getName());
        }
    }
//    ===================================== SUBQUERY =====================================================
      // SUBQUERY는 하이버네이트 ENTITY MANAGER를 사용 하든, QUERY DSL을 사용 하라
      // SPRING DATA JPQ 서브쿼리 사용 하려면 NATIVE QUERY 추천
//    ===================================== 비 연관관계 조인 ===============================================
    @Test
    public void findByUnReachableJoin(){
        List<Member> byUnReachableJoin = memberService.findByUnReachableJoin();
        for (Member member : byUnReachableJoin) {
            System.out.println(member.getAddress().getZipcode());
        }
    }


//    ===================================== Paging =======================================================
    // 기본 페이징
    @Test
    public void findMemberByAllPaging(){
        PageRequest sort = PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "name").and(Sort.by(Sort.Order.desc("id"))));
        Page<Member> members = memberService.findByAll(sort);
        System.out.println("size::" + members.getSize());
        System.out.println("totalElements::"+members.getTotalElements());
        System.out.println("totalPages::" + members.getTotalPages());
        System.out.println("number::" + members.getNumber());
        System.out.println("numberOfElements::" + members.getNumberOfElements());
        for (Member member : members) {
            String name = member.getName();
            System.out.println(name);
        }
    }
    @Test
    public void findMemberByNamePaging(){
        /**
         * JPQL로 PAGING 실행 시 카운트 쿼리가 자동을 따로 실행 된다. 카운트쿼리는 커스터마이징 가능 하다.
         */
        PageRequest sort = PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "name").and(Sort.by(Sort.Order.desc("id"))));
        Page<Member> members = memberService.findMemberByNameInPaging(sort);
        System.out.println("size::" + members.getSize());
        System.out.println("totalElements::"+members.getTotalElements());
        System.out.println("totalPages::" + members.getTotalPages());
        System.out.println("number::" + members.getNumber());
        System.out.println("numberOfElements::" + members.getNumberOfElements());
        for (Member member : members) {
            String name = member.getName();
            System.out.println(name);
        }
    }
    /**
     * 1. 성능과 JPA (즉시로딩 부터 성능 전략까지)
     * 2. QUERY DSL
     */
}

