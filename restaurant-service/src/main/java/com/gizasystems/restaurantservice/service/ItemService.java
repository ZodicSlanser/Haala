package com.gizasystems.restaurantservice.service;

import com.gizasystems.restaurantservice.DTOs.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    ItemDto createItem(ItemDto itemDto);

    ItemDto getItemById(Long itemId);

    List<ItemDto> getAllItems();

    ItemDto updateItem(Long itemId, ItemDto updatedItem);

    void deleteItem(Long itemId);

    List<ItemDto> getItemsByRestaurantId(Long restaurantId);

    List<ItemDto> getItemsByCategory(String category);

}

