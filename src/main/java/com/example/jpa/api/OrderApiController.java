package com.example.jpa.api;

import java.util.List;
import java.util.stream.Collectors;

import com.example.jpa.domain.order.Order;
import com.example.jpa.repository.OrderRepository;
import com.example.jpa.repository.OrderSearch;
import com.example.jpa.type.Address;
import com.example.jpa.type.DeliveryStatus;
import com.example.jpa.type.OrderStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    return orderRepository.findAll(new OrderSearch());
  }

  // #region SIMPLE ORDERS
  @GetMapping("/api/v1/simple-orders")
  public List<SimpleOrderDto> simpleOrders() {
    List<Order> orders = orderRepository.findOrderWithMemberAndDelivery();
    return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
  }

  @Data
  static class SimpleOrderDto {
    public SimpleOrderDto(Order order) {
      this.id = order.getId();
      this.memberId = order.getMember().getId();
      this.memberName = order.getMember().getName();
      this.orderStatus = order.getStatus();
      this.deliveryId = order.getDelivery().getId();
      this.deliveryAddress = order.getDelivery().getAddress();
      this.deilveryStatus = order.getDelivery().getStatus();
    }

    private Long id;
    private Long memberId;
    private String memberName;
    private OrderStatus orderStatus;

    private Long deliveryId;
    private Address deliveryAddress;
    private DeliveryStatus deilveryStatus;
  }
  // #endregion
}
