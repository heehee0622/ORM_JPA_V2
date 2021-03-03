package com.example.syshology.jpa.service;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.entity.Member;
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
        List<Member> findMembers = memberRepository.findMembersByName(member.getName());
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
        return memberRepository.findMembersByName(name);
    }

    public List<Member> findByQD() {
        return memberRepository.findOneQD();
    }

    public List<Member> findJoinQD() {
        return memberRepository.findJoinQD();
    }

    public List<Member> findByIdJoinFetchQD() {
        return memberRepository.findOneJoinFetchQD();
    }

    public List<Member> findOrderByIdDesc() {
        return memberRepository.findMembersByOrderByIdDesc();
    }

    public List<Member> findOrderByNameDesc() {
        return memberRepository.findMembersByOrderByNameDesc();
    }

    public List<Member> findOrderByQD() {
        Sort name = Sort.by(Sort.Order.desc("name"), Sort.Order.asc("id"));
        return memberRepository.findMembersByOrderByQD(name);
    }

    public List<Member> findWhereBetweenQD() {
        return memberRepository.findByIdBetweenQD(1L, 100L);
    }


    public List<Member> findWhereInQD(List<Long> ids) {
        return memberRepository.findByIdInQD(ids);
    }

    public List<Member> findWhereCompare() {
        return memberRepository.findByIdGreaterThanAndNameContaining(1L, "A");
    }

    public List<Member> findWhereCompareQD() {
        return memberRepository.findByIdGreaterThanAndNameLikeQD(1L, "A");
    }

    public List<Member> findByMaxNameGroupByBasicQD() {
        return memberRepository.findByMaxNameGroupByBasicQD();
    }
    public List<MemberDto> findBySumIdGroupByNameQD() {
        return memberRepository.findByidSumGroupByNameQD();
    }
    public List<MemberDto> findByIdSumGroupBySumHavingQD() {
        return memberRepository.findByIdSumGroupBySumHavingQD("A");
    }

    public List<Member> findByunReachableJoinQD() {
        return memberRepository.findByunRechableJoinQD();
    }

    public Page<Member> findByAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Page<Member> findMemberByNameInPaging(Pageable pageable) {
        return memberRepository.findByNameIn(Arrays.asList("A"), pageable);
    }
}