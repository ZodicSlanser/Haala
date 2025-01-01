package com.gizasystems.restaurantservice.service;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);

    RestaurantDto getRestaurantById(Long restaurantId);

    List<RestaurantDto> getAllRestaurants();

    RestaurantDto updateRestaurant(Long restaurantId, RestaurantDto updatedRestaurant);

    void deleteRestaurant(Long restaurantId);
}
