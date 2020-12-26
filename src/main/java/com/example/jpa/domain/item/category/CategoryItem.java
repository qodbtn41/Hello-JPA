package com.example.jpa.domain.item.category;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.jpa.domain.BaseEntity;
import com.example.jpa.domain.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryItem extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 8220089829885452952L;
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ITEM_ID")
  private Item item;
}
