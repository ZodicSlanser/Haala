package com.gizasystems.restaurantservice.service.impl;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.exceptions.ResourceNotFoundException;
import com.gizasystems.restaurantservice.mapper.RestaurantMapper;
import com.gizasystems.restaurantservice.repository.RestaurantRepository;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {

        Restaurant restaurant = RestaurantMapper.mapToRestaurant(restaurantDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantMapper.mapToRestaurantDto(savedRestaurant);
    }

    @Override
    public RestaurantDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant is not existing with given id: " + restaurantId));

        return RestaurantMapper.mapToRestaurantDto(restaurant);
    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        var restaurants = restaurantRepository.findAll();

        return restaurants.stream().map(RestaurantMapper::mapToRestaurantDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDto updateRestaurant(Long restaurantId, RestaurantDto updatedRestaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant is not existing with given id: " + restaurantId)
        );
        restaurant.setName(updatedRestaurantDto.getName());
        restaurant.setAddressId(updatedRestaurantDto.getAddressId());

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
}
