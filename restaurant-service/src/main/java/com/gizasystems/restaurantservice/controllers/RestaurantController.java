package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.OrderDTO;
import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.exception.UnauthorizedException;
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
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) throws Exception {

        RestaurantDto savedRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurant> getRestaurantById(HttpServletRequest request,
                                                        @PathVariable("id") Long restaurantId) {
        Long ownerId = getUserId(request);
        String role = getUserRole(request);
        checkAuthorized(request, restaurantId, role);


        Restaurant restaurantDto = restaurantService.getRestaurantById(restaurantId);


        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(HttpServletRequest request) {

        String role = getUserRole(request);
        Long ownerId = getUserId(request);

        if (role != null && !role.equals("OWNER")) {
            return ResponseEntity.ok(restaurantService.getAllRestaurants());
        }
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();

        // Filter restaurants to include only those where the user is an owner
        List<Restaurant> ownedRestaurants = allRestaurants.stream()
                .filter(restaurant -> !restaurant.getOwners().isEmpty() && restaurant.getOwners().stream().anyMatch(owner -> owner.getId().equals(ownerId)))
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
        String role = getUserRole(request);
        checkAuthorized(request, restaurantId, role);


        RestaurantDto updatedRestaurantDto = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);

        return ResponseEntity.ok(updatedRestaurantDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(HttpServletRequest request,
                                                   @PathVariable("id") Long restaurantId) {
        String role = getUserRole(request);

        checkAuthorized(request, restaurantId, role);

        restaurantService.deleteRestaurant(restaurantId);

        return ResponseEntity.ok("Restaurant deleted successfully!");
    }

    private void checkAuthorized(HttpServletRequest request, Long restaurantId, String role) {
        if (role != null && role.equals("OWNER")) {
            Long ownerId = getUserId(request);
            Restaurant existingRestaurant = restaurantService.getRestaurantById(restaurantId);

            if (existingRestaurant.getOwners().isEmpty() || existingRestaurant.getOwners().stream().noneMatch(owner -> owner.getId().equals(ownerId))) {
                throw new UnauthorizedException("You are not authorized to perform this action!");
            }

        }
    }


    @GetMapping("/owner/{ownerId}/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurantsByOwnerId(HttpServletRequest request,
                                                                          @PathVariable("ownerId") Long ownerId) throws Throwable {
        String role = getUserRole(request);
        if (role != null && !role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        List<RestaurantDto> restaurants = restaurantService.getRestaurantsByOwnerId(ownerId);


        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantDto> getRestaurantByName(
            @PathVariable("name") String name) {

        RestaurantDto restaurantDto = restaurantService.getRestaurantByName(name);

        return ResponseEntity.ok(restaurantDto);
    }

    @GetMapping("/placed-orders")
    public ResponseEntity<List<OrderDTO>> getPlacedOrders(HttpServletRequest request, @RequestParam Long restaurantId) {
        String role = getUserRole(request);
        checkAuthorized(request, restaurantId, role);
        List<OrderDTO> orders = restaurantService.getRestaurantOrders(restaurantId, "PLACED");
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/confirmed-orders")
    public ResponseEntity<List<OrderDTO>> getConfirmedOrders(HttpServletRequest request, @RequestParam("restaurantId") Long restaurantId) {

        String role = getUserRole(request);
        checkAuthorized(request, restaurantId, role);

        List<OrderDTO> orders = restaurantService.getRestaurantOrders(restaurantId, "PREPARING");
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/preparing")
    public ResponseEntity<OrderDTO> updateOrderPreparing(HttpServletRequest request, @RequestParam Long orderId, @RequestParam Long restaurantId) {
        String role = getUserRole(request);
        checkAuthorized(request, restaurantId, role );

        OrderDTO updatedOrder = restaurantService.updateRestaurantState(orderId, "PREPARING");
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/waiting")
    public ResponseEntity<OrderDTO> updateOrderWaiting(HttpServletRequest request, @RequestParam Long orderId, @RequestParam Long restaurantId) {
        String role = getUserRole(request);
        checkAuthorized(request, restaurantId, role);

        OrderDTO updatedOrder = restaurantService.updateRestaurantState(orderId, "WAITING_FOR_DELIVERY");
        return ResponseEntity.ok(updatedOrder);
    }

}
