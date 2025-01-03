package com.gizasystems.restaurantservice.service.impl;

import com.gizasystems.restaurantservice.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import com.gizasystems.restaurantservice.DTOs.ItemDto;
import com.gizasystems.restaurantservice.entites.Item;
import com.gizasystems.restaurantservice.mapper.ItemMapper;
import com.gizasystems.restaurantservice.repository.ItemRepository;
import com.gizasystems.restaurantservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto) {

        Item item = ItemMapper.mapToItem(itemDto);
        Item savedItem = itemRepository.save(item);

        return ItemMapper.mapToItemDto(savedItem);
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()->new ResourceNotFoundException("Item is not existing with given id: "+itemId));

        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();

        return items.stream().map((item)->ItemMapper.mapToItemDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemDto updatedItem) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                ()->new ResourceNotFoundException("Item is not existing with given id: "+itemId)
        );
        item.setName(updatedItem.getName());
        item.setDescription(updatedItem.getDescription());
        item.setPrice(updatedItem.getPrice());
        item.setRating(updatedItem.getRating());
        item.setCategory(updatedItem.getCategory());
        item.setQuantity(updatedItem.getQuantity());

        Item updatedItemObj = itemRepository.save(item);

        return ItemMapper.mapToItemDto(updatedItemObj);
    }

    @Override
    public void deleteItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(
                ()->new ResourceNotFoundException("Item is not existing with given id: "+itemId)
        );

        itemRepository.deleteById(itemId);

    }

}
