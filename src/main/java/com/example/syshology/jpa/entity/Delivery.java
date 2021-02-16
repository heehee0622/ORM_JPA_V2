package com.example.syshology.jpa.entity;

import com.example.syshology.jpa.type.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
 @Id @GeneratedValue
 @Column(name = "delivery_id")
 private Long id;
 @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
 @JsonIgnore
 private Order order;
 @Embedded
 private Address address;
 @Enumerated(EnumType.STRING)
 private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]

}