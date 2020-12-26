package com.example.jpa.repository;

import com.example.jpa.type.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
  private String memberName;
  private OrderStatus orderStatus;
}
