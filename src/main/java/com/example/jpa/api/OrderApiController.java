package com.example.jpa.api;

import java.util.List;
import java.util.stream.Collectors;

import com.example.jpa.domain.order.Order;
import com.example.jpa.domain.order.OrderItem;
import com.example.jpa.repository.OrderRepository;
import com.example.jpa.repository.query.OrderQueryDto;
import com.example.jpa.repository.query.OrderQueryRepository;
import com.example.jpa.type.Address;
import com.example.jpa.type.DeliveryStatus;
import com.example.jpa.type.OrderStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
  private final OrderRepository orderRepository;
  private final OrderQueryRepository orderQueryRepository;

  @GetMapping("/api/v1/orders")
  public List<OrderDto> ordersV1() {
    List<Order> orders = orderRepository.findAllWithItem();
    List<OrderDto> collect = orders.stream().map(OrderDto::new).collect(Collectors.toList());
    return collect;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2(@RequestParam(name = "offset", defaultValue = "0") int offset,
      @RequestParam(name = "limit", defaultValue = "100") int limit) {
    List<Order> orders = orderRepository.findOrderWithMemberDelivery(offset, limit);
    List<OrderDto> collect = orders.stream().map(OrderDto::new).collect(Collectors.toList());
    return collect;
  }

  @GetMapping("/api/v3/orders")
  public List<OrderQueryDto> ordersV3() {
    return orderQueryRepository.findAllByDto();
  }

  @Data
  static class OrderDto {
    public OrderDto(Order order) {
      this.id = order.getId();
      this.memberId = order.getMember().getId();
      this.memberName = order.getMember().getName();
      this.orderStatus = order.getStatus();
      this.deliveryId = order.getDelivery().getId();
      this.deliveryAddress = order.getDelivery().getAddress();
      this.deilveryStatus = order.getDelivery().getStatus();
      this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }

    private Long id;
    private Long memberId;
    private String memberName;
    private OrderStatus orderStatus;

    private Long deliveryId;
    private Address deliveryAddress;
    private DeliveryStatus deilveryStatus;

    private List<OrderItemDto> orderItems;
  }

  @Data
  static class OrderItemDto {
    public OrderItemDto(OrderItem orderItem) {
      id = orderItem.getId();
      name = orderItem.getItem().getName();
      count = orderItem.getCount();
      orderPrice = orderItem.getOrderPrice();
    }

    Long id;
    String name;
    int count;
    int orderPrice;
  }

  // #region SIMPLE ORDERS
  @GetMapping("/api/v1/simple-orders")
  public List<SimpleOrderDto> simpleOrders() {
    List<Order> orders = orderRepository.findOrderWithMemberDelivery();
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
