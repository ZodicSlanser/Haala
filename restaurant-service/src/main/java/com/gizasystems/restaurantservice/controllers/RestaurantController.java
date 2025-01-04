package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.gizasystems.restaurantservice.util.helperFunctions.*;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(HttpServletRequest request,
                                                          @RequestBody RestaurantDto restaurantDto){
        RestaurantDto savedRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(HttpServletRequest request, 
                                                           @PathVariable("id") Long restaurantId){
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(HttpServletRequest request){
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping("{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(HttpServletRequest request,
                                                          @PathVariable("id") Long restaurantId,
                                                          @RequestBody RestaurantDto updatedRestaurant){
        RestaurantDto restaurantDto = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);
        return ResponseEntity.ok(restaurantDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(HttpServletRequest request,
                                                   @PathVariable("id") Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok("Restaurant deleted successfully!");
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<RestaurantDto> getRestaurantByAddressId(@PathVariable("addressId") Long addressId) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantByAddressId(addressId);
        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<RestaurantDto> getRestaurantByOwnerId(@PathVariable("ownerId") Long ownerId) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantByOwnerId(ownerId);
        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping("/owner/{ownerId}/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurantsByOwnerId(@PathVariable("ownerId") Long ownerId) throws Throwable {
        List<RestaurantDto> restaurants = restaurantService.getRestaurantsByOwnerId(ownerId);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantDto> getRestaurantByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(restaurantService.getRestaurantByName(name));
    }

}
