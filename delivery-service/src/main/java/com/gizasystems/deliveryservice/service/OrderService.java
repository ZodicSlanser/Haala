package com.gizasystems.deliveryservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gizasystems.deliveryservice.dto.OrderDTO;

import java.util.List;

@FeignClient(value = "jplaceholder", url = "http://localhost:8082/orders")
public interface OrderService {
  // can be used to view all orders by state (for delivery person to view waiting orders)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  List<OrderDTO> getOrdersByState(@RequestParam("state") String state);

  // can be used to view 
  // - ON_WAY + deliveryPersonId => to get currently assigned order
  // - DELIVERED + deliveryPersonId => to get all delivered orders (for history or to calculate fees)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  List<OrderDTO> getAssignedOrders(@RequestParam("state") String state, @RequestParam("deliveryPersonId") Long deliveryPersonId);

  // can be used to mark orders as ON_WAY or DELIVERED
  @RequestMapping(value = "/{id}/state", method = RequestMethod.PUT)
  OrderDTO updateOrderState(@PathVariable("id") Long id, @RequestParam("state") String status);

  @RequestMapping(value = "/{id}/delivery-person", method = RequestMethod.PUT)
  OrderDTO assignDeliveryToOrder(@PathVariable("id") Long id, @RequestParam("deliveryPersonId") Long deliveryPersonId);
}

