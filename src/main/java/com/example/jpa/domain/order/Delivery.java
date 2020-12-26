package com.example.jpa.domain.order;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.jpa.domain.BaseEntity;
import com.example.jpa.type.Address;
import com.example.jpa.type.DeliveryStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "DELIVERY_ID")
  private String id;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  private Order order;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;
}
