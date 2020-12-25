package com.example.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "CATEGORY_ID")
  private String id;

  @ManyToOne
  @JoinColumn(name = "PARENT_ID")
  private Category parent;

  private String name;
}
