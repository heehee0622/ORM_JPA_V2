package com.example.syshology.jpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item {
 private String director;
 private String actor;
 @Builder
 public Movie(String name, int price, int stockQuantity) {
  super(name,price,stockQuantity);
 }

 public Movie() {
  super();
 }
}