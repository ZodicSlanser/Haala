package com.gizasystems.restaurantservice.service.impl;

import com.gizasystems.restaurantservice.DTOs.OrderDTO;
import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.client.OrderService;
import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.exceptions.ResourceNotFoundException;
import com.gizasystems.restaurantservice.mapper.RestaurantMapper;
import com.gizasystems.restaurantservice.repository.OwnerRepository;
import com.gizasystems.restaurantservice.repository.RestaurantRepository;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) throws Exception {

        Restaurant restaurant = RestaurantMapper.mapToRestaurant(restaurantDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantMapper.mapToRestaurantDto(savedRestaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant is not existing with given id: " + restaurantId));

        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants;
    }

    @Override
    public RestaurantDto updateRestaurant(Long restaurantId, RestaurantDto updatedRestaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant is not existing with given id: " + restaurantId)
        );
        restaurant.setName(updatedRestaurantDto.getName());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantMapper.mapToRestaurantDto(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant is not existing with given id: " + restaurantId)
        );

        restaurantRepository.deleteById(restaurantId);
    }


    @Override
    public List<RestaurantDto> getRestaurantsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + ownerId));
        List<Restaurant> restaurants = restaurantRepository.findRestaurantsByOwners(Collections.singletonList(owner));

        return restaurants.stream().map(RestaurantMapper::mapToRestaurantDto).collect(Collectors.toList());
    }


    @Override
    public RestaurantDto getRestaurantByName(String name) {
        Restaurant restaurant = restaurantRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with name: " + name));
        return RestaurantMapper.mapToRestaurantDto(restaurant);
    }

    public List<OrderDTO> getRestaurantOrders(Long restaurantId,String state) {

        return orderService.searchOrders(restaurantId, state).getBody();

    }

    public OrderDTO updateRestaurantState(Long orderId, String state) {
        return orderService.updateOrderState(orderId, state);
    }

}
