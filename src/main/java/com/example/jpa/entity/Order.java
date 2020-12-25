package com.example.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.jpa.type.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "ORDER_ID")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;
  private LocalDateTime orderDate;
  private OrderStatus status;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<OrderItem>();

  @OneToOne(fetch = FetchType.LAZY)
  private Delivery delivery;

  public void addOrderItem(OrderItem orderItem) {
    orderItem.setOrder(this);
  }
}
