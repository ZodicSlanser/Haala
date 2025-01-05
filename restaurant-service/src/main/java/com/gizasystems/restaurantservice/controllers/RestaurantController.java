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
@RequestMapping("/api/restaurants/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(HttpServletRequest request,
                                                          @RequestBody RestaurantDto restaurantDto) {

        Long ownerId = getUserId(request);

        // Validate that the restaurant contains a list of owners
        if (restaurantDto.getOwnersIds() == null || restaurantDto.getOwnersIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        // Validate that the owner invoking the endpoint is in the list of owners
        if (!restaurantDto.getOwnersIds().contains(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        // Validate that the restaurant has a list of item IDs
        if (restaurantDto.getItemsIds() == null || restaurantDto.getItemsIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        RestaurantDto savedRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(HttpServletRequest request,
                                                           @PathVariable("id") Long restaurantId) {
        Long ownerId = getUserId(request);

        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);

        // Check if the restaurant exists
        if (restaurantDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        // Validate that the owner is in the restaurant's owners list
        if (restaurantDto.getOwnersIds() == null || !restaurantDto.getOwnersIds().contains(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(HttpServletRequest request) {
        Long ownerId = getUserId(request);

        List<RestaurantDto> allRestaurants = restaurantService.getAllRestaurants();

        // Filter restaurants to include only those where the user is an owner
        List<RestaurantDto> ownedRestaurants = allRestaurants.stream()
                .filter(restaurant -> restaurant.getOwnersIds() != null && restaurant.getOwnersIds().contains(ownerId))
                .toList();

        // Check if the user owns any restaurants
        if (ownedRestaurants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        return ResponseEntity.ok(ownedRestaurants);
    }

    @PutMapping("{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(HttpServletRequest request,
                                                          @PathVariable("id") Long restaurantId,
                                                          @RequestBody RestaurantDto updatedRestaurant) {
        Long ownerId = getUserId(request);

        RestaurantDto existingRestaurant = restaurantService.getRestaurantById(restaurantId);

        if (existingRestaurant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        if (existingRestaurant.getOwnersIds() == null || !existingRestaurant.getOwnersIds().contains(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        RestaurantDto updatedRestaurantDto = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);

        return ResponseEntity.ok(updatedRestaurantDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(HttpServletRequest request,
                                                   @PathVariable("id") Long restaurantId) {
        Long ownerId = getUserId(request);

        RestaurantDto existingRestaurant = restaurantService.getRestaurantById(restaurantId);

        if (existingRestaurant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurant not found.");
        }

        if (existingRestaurant.getOwnersIds() == null || !existingRestaurant.getOwnersIds().contains(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You are not authorized to delete this restaurant.");
        }

        restaurantService.deleteRestaurant(restaurantId);

        return ResponseEntity.ok("Restaurant deleted successfully!");
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<RestaurantDto> getRestaurantByAddressId(HttpServletRequest request,
                                                                  @PathVariable("addressId") Long addressId) {
        Long ownerId = getUserId(request);

        RestaurantDto restaurantDto = restaurantService.getRestaurantByAddressId(addressId);

        if (restaurantDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        if (restaurantDto.getOwnersIds() == null || !restaurantDto.getOwnersIds().contains(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<RestaurantDto> getRestaurantByOwnerId(HttpServletRequest request,
                                                                @PathVariable("ownerId") Long ownerId) {
        Long authenticatedOwnerId = getUserId(request);

        if (!authenticatedOwnerId.equals(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        RestaurantDto restaurantDto = restaurantService.getRestaurantByOwnerId(ownerId);

        if (restaurantDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping("/owner/{ownerId}/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurantsByOwnerId(HttpServletRequest request,
                                                                          @PathVariable("ownerId") Long ownerId) throws Throwable {
        Long authenticatedOwnerId = getUserId(request); // Get the authenticated owner's ID

        if (!authenticatedOwnerId.equals(ownerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        List<RestaurantDto> restaurants = restaurantService.getRestaurantsByOwnerId(ownerId);

        if (restaurants == null || restaurants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantDto> getRestaurantByName(HttpServletRequest request,
                                                             @PathVariable("name") String name) {
        Long authenticatedOwnerId = getUserId(request);

        RestaurantDto restaurantDto = restaurantService.getRestaurantByName(name);

        if (restaurantDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        if (!restaurantDto.getOwnersIds().contains(authenticatedOwnerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        return ResponseEntity.ok(restaurantDto);
    }

}
