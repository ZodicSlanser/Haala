package com.gizasystems.restaurantservice.service.impl;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.exceptions.ResourceNotFoundException;
import com.gizasystems.restaurantservice.mapper.RestaurantMapper;
import com.gizasystems.restaurantservice.repository.RestaurantRepository;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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
        List<Restaurant> restaurants = restaurantRepository.findAll();

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

    @Override
    public RestaurantDto getRestaurantByAddressId(Long addressId) {
        Restaurant restaurant = restaurantRepository.findByAddressId(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with address ID: " + addressId));
        return RestaurantMapper.mapToRestaurantDto(restaurant);
    }

    @Override
    public RestaurantDto getRestaurantByOwnerId(Long ownerId) {
        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .filter(r -> r.getOwnerIds() != null && r.getOwnerIds().contains(ownerId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with owner ID: " + ownerId));
        return RestaurantMapper.mapToRestaurantDto(restaurant);
    }

    @Override
    public List<RestaurantDto> getRestaurantsByOwnerId(Long ownerId) throws Throwable {
        SimpleJpaRepository ownerRepository = null;
        Owner owner = (Owner) ownerRepository.findById(ownerId).orElseThrow(
                () -> new ResourceNotFoundException("Owner not found with ID: " + ownerId)
        );

        List<Long> restaurantIds = owner.getRestaurantIds();
        List<Restaurant> restaurants = restaurantRepository.findAllById(restaurantIds);

        return restaurants.stream()
                .map(RestaurantMapper::mapToRestaurantDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDto getRestaurantByName(String name) {
        Restaurant restaurant = restaurantRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with name: " + name));
        return RestaurantMapper.mapToRestaurantDto(restaurant);
    }

}
