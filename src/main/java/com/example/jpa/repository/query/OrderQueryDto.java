package com.example.jpa.repository.query;

import java.time.LocalDateTime;
import com.example.jpa.type.Address;
import com.example.jpa.type.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "orderId")
@AllArgsConstructor
public class OrderQueryDto {
  private Long orderId;
  private String memberName;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  private String itemName;
  private int orderPrice;
  private int count;

}
