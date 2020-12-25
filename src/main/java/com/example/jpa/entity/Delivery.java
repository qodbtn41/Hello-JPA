package com.example.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
  private String city;
  private String street;
  private String zipcode;
  private String status;
}
