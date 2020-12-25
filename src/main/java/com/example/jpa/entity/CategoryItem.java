package com.example.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryItem implements Serializable {
  @Id
  @ManyToOne
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;
  @Id
  @ManyToOne
  @JoinColumn(name = "ITEM_ID")
  private Item item;
}
