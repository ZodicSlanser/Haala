package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.DTOs.RestaurantDto;
import com.gizasystems.restaurantservice.entites.Restaurant;
import com.gizasystems.restaurantservice.service.OwnerService;
import com.gizasystems.restaurantservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import com.gizasystems.restaurantservice.DTOs.ItemDto;
import com.gizasystems.restaurantservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.gizasystems.restaurantservice.util.helperFunctions.*;

@RestController
@RequestMapping("/api/restaurants/items")

public class ItemController {
    @Autowired
    public ItemController(ItemService itemService, OwnerService ownerService, RestaurantService restaurantService) {
        this.itemService = itemService;

        this.restaurantService = restaurantService;
    }

    final private ItemService itemService;
    final private RestaurantService restaurantService;

    // The owner must be in the list of the owners belonging to that Restaurant being provided while creating the item.
    @PostMapping
    public ResponseEntity<ItemDto> createItem(HttpServletRequest request, @RequestBody ItemDto itemDto) {

        if (itemDto.getRestaurantId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String role = getUserRole(request);


        // Saftey: API Gateway
        if (role != null && role.equals("OWNER")) {
            Long ownerId = getUserId(request);
            Restaurant existingRestaurant = restaurantService.getRestaurantById(itemDto.getRestaurantId());

            if (existingRestaurant.getOwners().isEmpty() || existingRestaurant.getOwners().stream().noneMatch(owner -> owner.getId().equals(ownerId))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null);
            }
        }
        ItemDto savedItem = itemService.createItem(itemDto);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDto> getItemByID(HttpServletRequest request, @PathVariable("id") Long itemId) {
        ItemDto itemDto = itemService.getItemById(itemId);
        return ResponseEntity.ok(itemDto);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ItemDto>> getAllItemsByRestaurantID(@PathVariable("restaurantId") Long restaurantId) {
        // Get the items for the specific restaurant
        List<ItemDto> items = itemService.getItemsByRestaurantId(restaurantId);

        return ResponseEntity.ok(items);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(HttpServletRequest request) {

        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDto> updateItem(HttpServletRequest request,
                                              @PathVariable("id") Long itemId,
                                              @RequestBody ItemDto updatedItem) {

        Long ownerId = getUserId(request);
        List<RestaurantDto> ownerRestaurant = restaurantService.getRestaurantsByOwnerId(ownerId);

        // Check if the restaurantId belongs to the current user
        if (ownerRestaurant.stream().noneMatch(restaurant -> restaurant.getId().equals(updatedItem.getRestaurantId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ItemDto updatedItemDto = itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(updatedItemDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(HttpServletRequest request,
                                             @PathVariable("id") Long itemId) {


        ItemDto existingItem = itemService.getItemById(itemId);
        Long ownerId = getUserId(request);
        List<RestaurantDto> ownerRestaurant = restaurantService.getRestaurantsByOwnerId(ownerId);

        // Check if the restaurantId belongs to the current user
        if (ownerRestaurant.stream().noneMatch(restaurant -> restaurant.getId().equals(existingItem.getRestaurantId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully!");
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemDto>> getItemsByCategory(HttpServletRequest request,
                                                            @PathVariable String category) {

        List<ItemDto> items = itemService.getItemsByCategory(category);

        return ResponseEntity.ok(items);
    }
}
