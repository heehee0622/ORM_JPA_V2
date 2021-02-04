package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.dto.MemberDto;
import com.example.syshology.jpa.dto.MemberIdDto;
import com.example.syshology.jpa.entity.Member;
import com.example.syshology.jpa.projection.MemberProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{
    public List<Member> findByName(String name);
//    @PersistenceContext
//    protected EntityManager entityManager;
//    public void save(Member member){
//        entityManager.persist(member);
//    }
//
//    public Optional<Member> findOne(Long id){
//        Member member = entityManager.find(Member.class, id);
//        return Optional.ofNullable(member);
//    }
//
//    public List<Member> findAll(){
//        return entityManager.createQuery("select  m from Member m", Member.class).getResultList();
//    }
//
//    public List<Member> findByName(String name){
//        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
//                .setParameter("name", name).getResultList();
//    }
//
//    public List<Member> findOneWithJpql(String name) {
//        return entityManager.createQuery("select  m from Member m where m.name= :name", Member.class).setParameter("name", name).getResultList();
//
//    }
    @Query(value = "select m from Member m join m.orders")
    public List<Member> findOneWithJoinJpql();

    @Query(value = "select m from Member m ")
    public List<Member> findOneJpql();

    @Query(value = "select m from Member m join fetch m.orders")
    public List<Member> findOneJoinFetch();
    public List<Member> findMembersByOrderByIdDesc();
    public List<Member> findMembersByOrderByNameDesc();
    @Query(value = "select m from Member m ")
    public List<Member> findMembersByOrderByJpql(Sort sort);
    public List<Member> findByIdBetween(Long startId, Long endId);
    @Query(value = "SELECT m from Member m where m.id between :startId and :endId")
    public List<Member> findByIdBetweenJpql(Long startId, Long endId);
    public List<Member> findByIdIn(List<Long> idList);
    @Query(value = "select m from Member m where m.id in (:idList)")
    public List<Member> findByIdInJpql(List<Long> idList);
    public List<Member> findByIdGreaterThanAndNameContaining(Long id,String name);
    @Query(value = "select m from Member m where m.id >:id and m.name like %:name")
    public List<Member> findByIdGreaterThanAndNameLikeJpql(Long id, String name);
    @Query(value = "select Max(m) from Member m group by m.name ")
    public List<Member> findByAllGroupByBasic();
    @Query(value = "select new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id)) from Member m group by m.name ")
    public List<MemberDto> findByAllGroupBySum();
    @Query(value = "select  new com.example.syshology.jpa.dto.MemberDto(m.name, sum(m.id))  from Member m group by m.name  having m.name like %:name%")
    public List<MemberDto> findByAllGroupBySumHaving(String name);
    public List<MemberProjection> findMembersByOrderByIdAsc();
    @Query(value = "select  m.name as name, m.id as id  from Member m ")
    public List<MemberProjection> findMembersByOrderByIdAscJpql();
    public List<MemberIdDto> findByNameLike(String name);
    @Query(value = "select  new com.example.syshology.jpa.dto.MemberIdDto(m.id, m.name ) from Member m where m.name like %:name%")
    public List<MemberIdDto> findByNameLikeJpql(String name);
    @Query(value = "select m from Member m join m.orders s on m.name = s.delivery.address.city")
    public List<Member> findByUnReachalbeJoin();
    public Page<Member> findByNameIn(List<String> nameList, Pageable pageable);
}
