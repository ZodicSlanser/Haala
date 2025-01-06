package com.gizasystems.restaurantservice.service.impl;

import com.gizasystems.restaurantservice.entites.Owner;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.repository.OwnerRepository;
import com.gizasystems.restaurantservice.repository.RestaurantRepository;
import com.gizasystems.restaurantservice.service.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownersRepository;
    private final RestaurantRepository restaurantRepository;

    public OwnerServiceImpl(OwnerRepository ownersRepository, RestaurantRepository restaurantRepository) {
        this.ownersRepository = ownersRepository;
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public void addRestaurantToOwner(Long ownerId, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        Owner owner = ownersRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));
        owner.getRestaurants().add(restaurant);

        ownersRepository.save(owner);
    }

    @Override
    public Owner createOwner(Long ownerId) {
        Owner owner = new Owner();
        owner.setId(ownerId);
        return ownersRepository.save(owner);

    }
}
