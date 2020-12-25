package com.example.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.jpa.entity.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "ORDER_ITEM_ID")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "ORDER_ID")
  private Order order;
  @ManyToOne
  @JoinColumn(name = "ITEM_ID")
  private Item item;
  private Integer orderPrice;
  private Integer count;

}
