package com.example.jpa.service;

import java.util.List;

import com.example.jpa.domain.item.Item;
import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.order.Delivery;
import com.example.jpa.domain.order.Order;
import com.example.jpa.domain.order.OrderItem;
import com.example.jpa.repository.ItemRepository;
import com.example.jpa.repository.MemberRepository;
import com.example.jpa.repository.OrderRepository;
import com.example.jpa.repository.OrderSearch;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  /**
   * 주문
   * 
   * @param memberId
   * @param itemId
   * @param count
   * @return
   */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);
    // 배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getHomeAddress());
    // 주문상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
    // 주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);
    // 주문 저장
    orderRepository.save(order);
    return order.getId();
  }

  // 취소
  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    order.cancel();
  }

  // 검색
  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAll(orderSearch);
  }
}
