package com.gizasystems.restaurantservice.service;

import com.gizasystems.restaurantservice.DTOs.OrderDTO;
import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto) throws Exception;

    Restaurant getRestaurantById(Long restaurantId);

    List<Restaurant> getAllRestaurants();

    RestaurantDto updateRestaurant(Long restaurantId, RestaurantDto updatedRestaurant);

    void deleteRestaurant(Long restaurantId);

    List<RestaurantDto> getRestaurantsByOwnerId(Long ownerId);

    RestaurantDto getRestaurantByName(String name);

    List<OrderDTO> getRestaurantOrders(Long restaurantId,String state);

    OrderDTO updateRestaurantState(Long orderId, String state);
}
