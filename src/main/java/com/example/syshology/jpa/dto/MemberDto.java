package com.example.syshology.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

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
public class MemberDto {
    private String name;
    private Long count;
}
