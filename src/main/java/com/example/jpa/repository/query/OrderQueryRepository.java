package com.example.jpa.repository.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
  private final EntityManager em;

  // 조인에 의한 중복
  // 직접 중복 ROW를 합치는 작업을 해야한다.
  // 페이징이 불가능하다.
  public List<OrderQueryDto> findAllByDto() {
    return em.createQuery(
        "select new com.example.jpa.repository.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"
            + " from Order o" + " join o.member m" + " join o.delivery d" + " join o.orderItems oi" + " join oi.item i",
        OrderQueryDto.class).getResultList();
  }

}
