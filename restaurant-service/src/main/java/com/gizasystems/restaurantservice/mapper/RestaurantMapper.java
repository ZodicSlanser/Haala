package com.gizasystems.restaurantservice.mapper;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Restaurant;

public class RestaurantMapper {
    public static RestaurantDto mapToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddressId(),
                restaurant.getOwnerIds(),
                restaurant.getItemIds()
        );
    }

    public static Restaurant mapToRestaurant(RestaurantDto restaurantDto) {
        return new Restaurant(
                restaurantDto.getId(),
                restaurantDto.getName(),
                restaurantDto.getAddressId(),
                restaurantDto.getOwnerIds(),
                restaurantDto.getItemIds()
        );
    }
}
