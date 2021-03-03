package com.example.syshology.jpa.service;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.repository.MemberRepository;
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
    @Autowired
    MemberRepository memberRepository;

    //  ============================================ 기본 조회 테스트 ==========================================
    @Test
    public void findMembersWithQD() {
///        1. id 조건으로 조회 하지 않기 때문에 엔티티 연관관계를 보고 조인하지 않는다.
//        2. 멤버를 조회 후 로딩 된 엔티티에서 연관관계의 fetch 전략을 보고 즉시로딩이면 조회 한다. (현재 fetch batch가 설정되어 있기에 in 조건으로 하나의 쿼리로  조회한다.)
        List<Member> members = memberService.findByQD();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }
    @Test
    /**
     * SELECT m.orders from Member m; // Member 안에 eager로 되어 있고, m을 조회 하면, 자동으로 orders 조회 한다. 하지만 쿼리에서 m.orders를 조호 하면 자동으로 조인 한다.
     */
    public void findOrderInMemberQD(){
        memberRepository.findOrdersInMemberQD();
    }

    @Test
    public void findMemberWithJoinQD() {
        // 1. JPQL 사용시 기본 엔티티 연관관계를 보고 조인하지 않는다.
        // 2. 멤버를 조회 후 로딩 된 엔티티에서 연관관계의 fetch 전략을 보고 즉시로딩이면 조회 한다.
        List<Member> byIdJoinJpql = memberService.findJoinQD();
        for (Member member : byIdJoinJpql) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findMemberJoinFetchQD() {
        // 1. JPQL 사용시 기본 엔티티 연관관계를 보고 조인하지 않는다. 페치 조인은 타겟 대상도 한 쿼리로 로딩 한다.
        // 2. fetch 조인을 사용 하여서 N+1 쿼리를 하나의 쿼리로 바꿀 수 있다.
        List<Member> byIdJoinJpql = memberService.findByIdJoinFetchQD();
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
    public void findMemberOrderByQD() {
        memberService.findOrderByQD();
    }

    @Test
    public void findAllOrderBy() {
        List<Member> membersWithSort = memberService.findMembersWithSort();
        for (Member member : membersWithSort) {
        System.out.println(member.getAddress().getCity());
        }
    }

    //    ===================================== WHERE, BETWEEN, IN, 비교문, like ====================================

    @Test
    public void findWhereBetweenQD() {
        List<Member> members = memberService.findWhereBetweenQD();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findWhereInQD() {
        List<Member> members = memberService.findWhereInQD(Arrays.asList(8L, 10L));
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
    public void findWhereCompareQD() {
        List<Member> members = memberService.findWhereCompareQD();
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    //    ===================================== GROUP BY HAVING ==============================================
    @Test
    public void findByMaxNameGroupByBasic() {
        List<Member> byGroupByBasic = memberService.findByMaxNameGroupByBasicQD();
        byGroupByBasic.forEach(System.out::println);
    }

    @Test
    public void findBySumIdGroupByNameQD() {
        /**
         * 쿼리에 DTO를 매핑 해서 값을 조회 했기 때문에 필요한 컬럼만 select 에 포함 된다.
         */
        List<MemberDto> byGroupByBasic = memberService.findBySumIdGroupByNameQD();
        for (MemberDto member : byGroupByBasic) {
            System.out.println(member.getName());
        }
    }

    @Test
    public void findByIdSumGroupByBasicSumHaving() {
        /**
         * 쿼리에 DTO를 매핑 해서 값을 조회 했기 때문에 필요한 컬럼만 select 에 포함 된다.
         */
        List<MemberDto> byGroupByBasic = memberService.findByIdSumGroupBySumHavingQD();
        for (MemberDto member : byGroupByBasic) {
            System.out.println(member.getName());
        }
    }

//    ===================================== SUBQUERY =====================================================
      // SUBQUERY는 하이버네이트 ENTITY MANAGER를 사용 하든, QUERY DSL을 사용 하라
      // SPRING DATA JPQ 서브쿼리 사용 하려면 NATIVE QUERY 추천
//    ===================================== 비 연관관계 조인 ===============================================
    @Test
    public void findByunReachableJoin(){
        List<Member> members = memberService.findByunReachableJoinQD();
        for (Member member : members) {
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

}

