package com.example.jpa.demojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import com.example.jpa.domain.item.Book;
import com.example.jpa.domain.item.Item;
import com.example.jpa.service.ItemService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemServiceTest {
  @Autowired
  ItemService itemService;
  @Autowired
  EntityManager em;

  @Test
  public void addItem() throws Exception {
    Item item = new Book();
    item.setName("시골 JPA");
    item.setPrice(10000);
    item.setStockQuantity(10);

    Long itemId = itemService.saveItem(item);

    Item findItem = itemService.findItem(itemId);
    assertEquals(itemId, findItem.getId(), "저장이 잘 됐는지 확인");
  }

  @Test
  public void updateTest() throws Exception {
    Book book = em.find(Book.class, 1L);

    book.setName("ASDFASDF");

  }
}
