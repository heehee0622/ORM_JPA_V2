package com.example.syshology.jpa.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-03
 * Time: 오후 1:44
 * Project : IntelliJ IDEA
 */
public interface MemberProjection {
    String getName();
    String getId();

    @Value("#{target.id +''+ target.name} ")
    String getValues();

}
