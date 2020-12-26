package com.example.jpa.service;

import java.util.List;

import com.example.jpa.domain.item.Item;
import com.example.jpa.repository.ItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  @Transactional
  public Long saveItem(Item item) {
    return itemRepository.save(item);
  }

  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  public Item findItem(Long itemId) {
    return itemRepository.findOne(itemId);
  }
}
