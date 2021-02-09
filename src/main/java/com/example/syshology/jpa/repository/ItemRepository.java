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


}
