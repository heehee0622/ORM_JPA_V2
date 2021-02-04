package com.example.syshology.jpa.service;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.dto.MemberIdDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.projection.MemberProjection;
import com.example.syshology.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) throw new IllegalStateException("이미 존재하는 회원 입니다.");
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findMembersWithSort() {
        Sort nameSort = Sort.by(Sort.Direction.DESC, "name");
        return memberRepository.findAll(nameSort);
    }
    public Member findOne(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new NullPointerException("회원이 존재하지 않습니다."));
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public List<Member> findByJpql() {
        return memberRepository.findOneJpql();
    }

    public List<Member> findByIdJoinJpql() {
        return memberRepository.findOneWithJoinJpql();
    }

    public List<Member> findByIdJoinFetchJpql() {
        return memberRepository.findOneJoinFetch();
    }

    public List<Member> findOrderByIdDesc() {
        return memberRepository.findMembersByOrderByIdDesc();
    }

    public List<Member> findOrderByNameDesc() {
        return memberRepository.findMembersByOrderByNameDesc();
    }

    public List<Member> findOrderByJpql() {
        Sort name = Sort.by(Sort.Direction.DESC, "name");
        return memberRepository.findMembersByOrderByJpql(name);
    }

    public List<Member> findWhereBtween() {
        return memberRepository.findByIdBetween(1L, 100L);
    }
    public List<Member> findWhereBetweenJpql() {
        return memberRepository.findByIdBetweenJpql(1L, 100L);
    }

    public List<Member> findWhereIn(List<Long> ids) {
        return memberRepository.findByIdIn(ids);
    }

    public List<Member> findWhereInJpql(List<Long> ids) {
        return memberRepository.findByIdInJpql(ids);
    }

    public List<Member> findWhereCompare() {
        return memberRepository.findByIdGreaterThanAndNameContaining(1L, "A");
    }

    public List<Member> findWhereCompareJpql() {
        return memberRepository.findByIdGreaterThanAndNameLikeJpql(1L, "A");
    }

    public List<Member> findByGroupByBasic(){
      return   memberRepository.findByAllGroupByBasic();
    }
    public List<MemberDto> findByGroupBySum(){
        return memberRepository.findByAllGroupBySum();
    }
    public List<MemberDto> findByGroupBySumHaving(){
        return memberRepository.findByAllGroupBySumHaving("A");
    }
     public List<MemberProjection> findOrderByNameAsc(){
        return memberRepository.findMembersByOrderByIdAsc();
     }

    public List<MemberProjection> findOrderByNameAscJpql(){
        return memberRepository.findMembersByOrderByIdAscJpql();
    }
    public List<MemberIdDto> findByNameLike(){
        return memberRepository.findByNameLike("A");
    }
    public List<MemberIdDto> findByNameLikeJpql(){
        return memberRepository.findByNameLikeJpql("A");
    }
    public List<Member> findByUnReachableJoin(){
        return memberRepository.findByUnReachalbeJoin();
    }
    public Page<Member> findByAll(Pageable pageable){
        return memberRepository.findAll(pageable);
    }
    public Page<Member> findMemberByNameInPaging(Pageable pageable){
        return memberRepository.findByNameIn(Arrays.asList("A"), pageable);
    }
}