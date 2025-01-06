package com.gizasystems.restaurantservice.mapper;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Item;
import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.repository.ItemRepository;
import com.gizasystems.restaurantservice.repository.OwnerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {

    static OwnerRepository ownerRepository;
    static ItemRepository itemRepository;

    RestaurantMapper(OwnerRepository ownerRepository, ItemRepository itemRepository) {
        this.ownerRepository = ownerRepository;
        this.itemRepository = itemRepository;
    }

    public static RestaurantDto mapToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getOwners().stream().map(owner -> owner.getId()).toList(),
                restaurant.getItems().stream().map(item -> item.getId()).toList()
        );
    }

    public static Restaurant mapToRestaurant(RestaurantDto restaurantDto) throws Exception {
        List<Owner> owners = restaurantDto.getOwnersIds().stream().map(ownerId -> {
            try {
                return ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("owner not found"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
        List<Item> items = restaurantDto.getItemsIds().stream().map(itemId -> {
            try {
                return itemRepository.findById(itemId).orElseThrow(() -> new Exception("item not found"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();


        return new Restaurant(
                restaurantDto.getName(),
                items,
                owners
        );
    }
}
