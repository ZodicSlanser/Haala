package com.gizasystems.restaurantservice.mapper;

import com.gizasystems.restaurantservice.DTOs.ItemDto;
import com.gizasystems.restaurantservice.entites.Item;

public class ItemMapper {
    public static ItemDto mapToItemDto(Item item){
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getRating(),
                item.getCategory(),
                item.getQuantity()
//                item.isAvailability()               /// TODO: is it correct? no getAvailability ya3ni
        );
    }

    public static Item mapToItem(ItemDto itemDto){
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                itemDto.getRating(),
                itemDto.getCategory(),
                itemDto.getQuantity()
//                itemDto.isAvailability()               /// TODO: is it correct? no getAvailability ya3ni
        );
    }

}