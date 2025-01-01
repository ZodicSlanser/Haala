package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    // Build Add Restaurant REST API
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto){
        RestaurantDto savedRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }

    // Build Get Restaurant REST API
    @GetMapping("{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable("id") Long restaurantId){
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurantDto);
    }

    // Build Get All Restaurants REST API
    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(){
        var restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // Build Update Restaurant REST API
    @PutMapping("{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("id") Long restaurantId,
                                                          @RequestBody RestaurantDto updatedRestaurant){
        RestaurantDto restaurantDto = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);
        return ResponseEntity.ok(restaurantDto);
    }

    // Build Delete Restaurant REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok("Restaurant deleted successfully!");
    }
}
