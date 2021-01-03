package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.jpa.domain.order.Order;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
  private final EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

  public List<Order> findAll(OrderSearch orderSearch) {
    return em
        .createQuery("SELECT o FROM Order o join o.member m" + " where o.status = :status" + " and m.name like :name",
            Order.class)
        .setParameter("status", orderSearch.getOrderStatus()).setParameter("name", orderSearch.getMemberName())
        .setMaxResults(1000).getResultList();
  }

  public List<Order> findOrderWithMemberAndDelivery() {
    return em.createQuery("SELECT o FROM Order o join fetch o.member m join fetch o.delivery d", Order.class)
        .getResultList();
  }
}
