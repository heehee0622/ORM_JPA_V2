package com.example.syshology.jpa.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
public class JWTResDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;

    public JWTResDto(String jwttoken) {
        this.token = jwttoken;
    }



}