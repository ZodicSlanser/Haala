package com.gizasystems.restaurantservice.client;

import com.gizasystems.restaurantservice.DTOs.OrderDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.QueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderService {
    // can be used to view all orders by state (for delivery person to view waiting orders)
    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    List<OrderDTO> getOrdersByState(@RequestParam("state") String state);

    // can be used to view
    // - ON_WAY + deliveryPersonId => to get currently assigned order
    // - DELIVERED + deliveryPersonId => to get all delivered orders (for history or to calculate fees)
    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    List<OrderDTO> getAssignedOrders(@RequestParam("state") String state, @RequestParam("deliveryPersonId") Long deliveryPersonId);

    // can be used to mark orders as ON_WAY or DELIVERED
    @RequestMapping(value = "/api/orders/{id}/state", method = RequestMethod.PUT)
    OrderDTO updateOrderState(@PathVariable("id") Long id, @RequestParam("state") String status);

    @RequestMapping(value = "/api/orders/{id}/delivery-person", method = RequestMethod.PUT)
    OrderDTO assignDeliveryToOrder(@PathVariable("id") Long id, @RequestParam("deliveryPersonId") Long deliveryPersonId);

    @GetMapping("/api/orders")
    ResponseEntity<List<OrderDTO>> searchOrders(@RequestParam("restaurantId") Long restaurantId,@RequestParam("state") String state);
}

