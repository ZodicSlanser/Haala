package com.gizasystems.restaurantservice.controllers;

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

@AllArgsConstructor
@RestController
@RequestMapping("/items")

public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(HttpServletRequest request, @RequestBody ItemDto itemDto){
        Long ownerId = getUserId(request);
        if(ownerId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        ItemDto savedItem = itemService.createItem(itemDto);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDto> getItemByID(HttpServletRequest request, @PathVariable("id") Long itemId){
        Long ownerId = getUserId(request);
        if(ownerId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        ItemDto itemDto = itemService.getItemById(itemId);
        return ResponseEntity.ok(itemDto);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(HttpServletRequest request){
        Long ownerId = getUserId(request);
        if(ownerId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDto> updateItem(HttpServletRequest request, 
                                              @PathVariable("id")Long itemId,
                                              @RequestBody ItemDto updatedItem){
        Long ownerId = getUserId(request);
        if(ownerId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        ItemDto itemDto = itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(itemDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(HttpServletRequest request, 
                                             @PathVariable("id")Long itemId){
        Long ownerId = getUserId(request);
        if(ownerId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully!");
    }
}
