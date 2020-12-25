package com.example.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private Integer price;
  private Integer stockQuantity;
}
