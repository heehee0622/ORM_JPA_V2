package com.example.syshology.jpa.dto;

import com.example.syshology.jpa.entity.Address;
import com.example.syshology.jpa.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-02-02
 * Time: 오후 4:41
 * Project : IntelliJ IDEA
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberIdDto {
    private Long id;
    private String name;
//    private Address address;
//    private List<Order> orders = new ArrayList<>();
}
