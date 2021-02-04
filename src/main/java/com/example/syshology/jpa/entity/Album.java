package com.example.syshology.jpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
public class Album extends Item {
 private String artist;
 private String etc;

 @Builder
 public Album(String name, int price, int stockQuantity) {
  super(name, price, stockQuantity);
 }

 public Album() {
  super();
 }
}