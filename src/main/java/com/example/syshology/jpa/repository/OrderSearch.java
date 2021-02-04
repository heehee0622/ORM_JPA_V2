package com.example.syshology.jpa.repository;

import com.example.syshology.jpa.type.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 3:00
 * Project : IntelliJ IDEA
 */
@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
