package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gizasystems.restaurantservice.util.helperFunctions.getOwnerId;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    // Build Add Restaurant REST API
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        RestaurantDto savedRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }

    // Build Get Restaurant REST API
    @GetMapping("{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable("id") Long restaurantId){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurantDto);
    }

    // Build Get All Restaurants REST API
    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        var restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // Build Update Restaurant REST API
    @PutMapping("{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("id") Long restaurantId,
                                                          @RequestBody RestaurantDto updatedRestaurant){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        RestaurantDto restaurantDto = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);
        return ResponseEntity.ok(restaurantDto);
    }

    // Build Delete Restaurant REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long restaurantId){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok("Restaurant deleted successfully!");
    }
}
