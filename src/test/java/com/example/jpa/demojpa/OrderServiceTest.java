package com.example.jpa.demojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;

import com.example.jpa.domain.item.Book;
import com.example.jpa.domain.item.Item;
import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.order.Order;
import com.example.jpa.exception.NotEnoughStockException;
import com.example.jpa.repository.OrderRepository;
import com.example.jpa.service.OrderService;
import com.example.jpa.type.Address;
import com.example.jpa.type.OrderStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderServiceTest {
  @Autowired
  OrderService orderService;
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  EntityManager em;

  @Test
  @Rollback(value = false)
  public void order() throws Exception {
    // given
    Member member = createMember();
    Book book = createBook("시골 JPA", 10000, 10);

    int orderCount = 2;
    // when
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
    em.flush();
    // then
    Order findOrder = orderRepository.findOne(orderId);

    assertEquals(orderId, findOrder.getId());
    assertEquals(OrderStatus.ORDER, findOrder.getStatus(), "상품 주문시 상태는 ORDER");
    assertEquals(1, findOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
    assertEquals(10000 * orderCount, findOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
    assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
  }

  @Test
  public void cancel() throws Exception {
    // given
    Member member = createMember();
    Book book = createBook("시골 JPA", 10000, 10);

    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
    // when
    orderService.cancelOrder(orderId);
    // then
    Order findOrder = orderRepository.findOne(orderId);

    assertEquals(OrderStatus.CANCEL, findOrder.getStatus(), "주문 취소시 상태는 CANCEL 이다.");
    assertEquals(10, book.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

  }

  @Test
  public void itemCountMaxCheck() throws Exception {
    // given
    Member member = createMember();
    Item item = createBook("시골 JPA", 10000, 10);
    // when
    int orderCount = 11;
    // then
    NotEnoughStockException thrown = assertThrows(NotEnoughStockException.class,
        () -> orderService.order(member.getId(), item.getId(), orderCount));
    assertEquals("need more stock", thrown.getMessage());
  }

  private Book createBook(String name, int price, int quantity) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(quantity);
    em.persist(book);
    return book;
  }

  private Member createMember() {
    Member member = new Member();
    member.setName("회원1");
    member.setHomeAddress(new Address("서울", "rkdrk", "123-123"));
    em.persist(member);
    return member;
  }
}
