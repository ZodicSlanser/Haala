package com.gizasystems.restaurantservice.mapper;

import com.gizasystems.restaurantservice.DTOs.ItemDto;
import com.gizasystems.restaurantservice.entites.Item;
import com.gizasystems.restaurantservice.entites.Restaurant;

public class ItemMapper {

    public static ItemDto mapToItemDto(Item item){
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getRating(),
                item.getCategory(),
                item.getQuantity(),
                item.getRestaurant() != null ? item.getRestaurant().getId() : null // Mapping restaurantId
        );
    }

    public static Item mapToItem(ItemDto itemDto, Restaurant restaurant){
        Item item = new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                itemDto.getRating(),
                itemDto.getCategory(),
                itemDto.getQuantity()
        );

        item.setRestaurant(restaurant); // Set the restaurant to the item

        return item;
    }
}
