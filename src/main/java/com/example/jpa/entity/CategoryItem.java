package com.example.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.jpa.entity.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryItem extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 8220089829885452952L;
  @Id
  @ManyToOne
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;
  @Id
  @ManyToOne
  @JoinColumn(name = "ITEM_ID")
  private Item item;
}
