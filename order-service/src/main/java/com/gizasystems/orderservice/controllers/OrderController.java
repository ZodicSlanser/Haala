package com.gizasystems.orderservice.controllers;

import com.gizasystems.orderservice.DTOs.OrderSearchCriteria;
import com.gizasystems.orderservice.DTOs.PlaceOrderDTO;
import com.gizasystems.orderservice.entites.Order;
import com.gizasystems.orderservice.enums.OrderState;
import com.gizasystems.orderservice.service.OrderService;
import com.gizasystems.orderservice.service.OrderStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderController is a REST controller that handles HTTP requests related to orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService, OrderStateService orderStateService) {
        this.orderService = orderService;
    }

    /**
     * Places a new order.
     *
     * @param placeOrderDTO the data transfer object containing order details
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
        Order order = orderService.createOrder(placeOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the order with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Searches for orders based on the specified criteria.
     *
     * @param criteria the criteria to search orders
     * @return the list of orders matching the criteria
     */
    @GetMapping
    public ResponseEntity<List<Order>> searchOrders(OrderSearchCriteria criteria) {
        List<Order> orders = orderService.searchOrders(criteria);
        return ResponseEntity.ok(orders);
    }

    /**
     * Updates the state of an order.
     *
     * @param id    the ID of the order
     * @param state the new state of the order
     * @return the updated order
     */
    @PutMapping("/{id}/state")
    public ResponseEntity<Order> updateOrderState(@PathVariable Long id, @RequestParam OrderState state) {
        Order updatedOrder = orderService.updateOrderState(id, state);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }


    /**
     * Assigns a delivery person to an order.
     *
     * @param id               the ID of the order
     * @param deliveryPersonId the ID of the delivery person
     * @return the updated order
     */
    @PutMapping("/{id}/delivery-person")
    public ResponseEntity<Order> assignDeliveryPerson(@PathVariable Long id, @RequestParam Long deliveryPersonId) {
        Order updatedOrder = orderService.assignDeliveryPerson(id, deliveryPersonId);
        return ResponseEntity.ok(updatedOrder);
    }


}
