package com.gizasystems.restaurantservice.controllers;

import com.gizasystems.restaurantservice.service.OwnerService;
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
    public ItemController(ItemService itemService, OwnerService ownerService){
        this.itemService = itemService;

        this.ownerService = ownerService;
    }
    final private ItemService itemService;
    final private OwnerService ownerService;

    // The owner must be in the list of the owners belonging to that Restaurant being provided while creating the item.
    @PostMapping
    public ResponseEntity<ItemDto> createItem(HttpServletRequest request, @RequestBody ItemDto itemDto){
        Long ownerId = getUserId(request);
        List<Long> ownerRestaurantIds = ownerService.getRestaurantsByOwnerId(ownerId);

        if(itemDto.getRestaurantId() == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!ownerRestaurantIds.contains(itemDto.getRestaurantId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ItemDto savedItem = itemService.createItem(itemDto);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDto> getItemByID(HttpServletRequest request, @PathVariable("id") Long itemId){
        Long ownerId = getUserId(request);
        List<Long> ownerRestaurantIds = ownerService.getRestaurantsByOwnerId(ownerId);

        ItemDto itemDto = itemService.getItemById(itemId);

        if (itemDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!ownerRestaurantIds.contains(itemDto.getRestaurantId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(itemDto);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ItemDto>> getAllItemsByRestaurantID(HttpServletRequest request, @PathVariable("restaurantId") Long restaurantId){

        Long ownerId = getUserId(request);
        List<Long> ownerRestaurantIds = ownerService.getRestaurantsByOwnerId(ownerId);

        // Check if the restaurantId belongs to the current user
        if (!ownerRestaurantIds.contains(restaurantId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get the items for the specific restaurant
        List<ItemDto> items = itemService.getItemsByRestaurantId(restaurantId);

        return ResponseEntity.ok(items);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(HttpServletRequest request){

        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDto> updateItem(HttpServletRequest request,
                                              @PathVariable("id") Long itemId,
                                              @RequestBody ItemDto updatedItem) {

        Long ownerId = getUserId(request);
        List<Long> ownerRestaurantIds = ownerService.getRestaurantsByOwnerId(ownerId);

        ItemDto existingItem = itemService.getItemById(itemId);

        if (existingItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!ownerRestaurantIds.contains(existingItem.getRestaurantId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ItemDto updatedItemDto = itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(updatedItemDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(HttpServletRequest request,
                                             @PathVariable("id") Long itemId) {

        Long ownerId = getUserId(request);
        List<Long> ownerRestaurantIds = ownerService.getRestaurantsByOwnerId(ownerId);

        ItemDto existingItem = itemService.getItemById(itemId);

        if (existingItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!ownerRestaurantIds.contains(existingItem.getRestaurantId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully!");
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemDto>> getItemsByCategory(HttpServletRequest request,
                                                            @PathVariable String category) {
        Long ownerId = getUserId(request);
        List<Long> ownerRestaurantIds = ownerService.getRestaurantsByOwnerId(ownerId);

        List<ItemDto> items = itemService.getItemsByCategory(category);

        List<ItemDto> filteredItems = items.stream()
                .filter(item -> ownerRestaurantIds.contains(item.getRestaurantId()))
                .toList();

        return ResponseEntity.ok(filteredItems);
    }
}
