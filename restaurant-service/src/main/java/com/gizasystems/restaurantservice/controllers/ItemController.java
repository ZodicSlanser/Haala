package com.gizasystems.restaurantservice.controllers;

import lombok.AllArgsConstructor;
import com.gizasystems.restaurantservice.DTOs.ItemDto;
import com.gizasystems.restaurantservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gizasystems.restaurantservice.util.helperFunctions.getOwnerId;

@AllArgsConstructor
@RestController
@RequestMapping("/items")

public class ItemController {
    @Autowired
    private ItemService itemService;

    // Build Add Item REST API
    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        ItemDto savedItem = itemService.createItem(itemDto);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // Build Get Item REST API
    @GetMapping("{id}")
    public ResponseEntity<ItemDto> getItemByID(@PathVariable("id") Long itemId){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        ItemDto itemDto = itemService.getItemById(itemId);
        return ResponseEntity.ok(itemDto);
    }

    // Build Get All Items REST API
    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        var items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Build Update Item REST API
    @PutMapping("{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable("id")Long itemId,
                                              @RequestBody ItemDto updatedItem){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        ItemDto itemDto = itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(itemDto);
    }

    // Build Delete Item REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id")Long itemId){
        Long ownerId = getOwnerId();
        if(ownerId == null) return ResponseEntity.badRequest().build();

        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully!");
    }
}