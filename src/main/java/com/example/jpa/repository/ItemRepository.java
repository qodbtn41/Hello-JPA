package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.jpa.domain.item.Item;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
  private final EntityManager em;

  /**
   * 상품 저장
   * 
   * @param item
   */
  public Long save(Item item) {
    if (item.getId() == null) {
      em.persist(item);
    } else {
      em.merge(item);
    }

    return item.getId();
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("SELECT i from Item i", Item.class).getResultList();
  }
}
