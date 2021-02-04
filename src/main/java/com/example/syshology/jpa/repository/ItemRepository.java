package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 2:26
 * Project : IntelliJ IDEA
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
//    @PersistenceContext
//    protected EntityManager entityManager;
//    public void save(Item item){
//        if (item.getId() == null) {
//            entityManager.persist(item);
//        }else {
//            entityManager.merge(item);
//        }
//    }
//
//    public Optional<Item> findOne(Long id){
//        Item item = entityManager.find(Item.class, id);
//        return Optional.ofNullable(item);
//    }
//    public List<Item> findAll(){
//        return entityManager.createQuery("select i from Item  i", Item.class).getResultList();
//    }

}
