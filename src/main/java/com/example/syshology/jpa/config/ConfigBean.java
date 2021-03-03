package com.example.syshology.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-22
 * Time: 오후 5:25
 * Project : IntelliJ IDEA
 */
@Configuration
public class ConfigBean {
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}
