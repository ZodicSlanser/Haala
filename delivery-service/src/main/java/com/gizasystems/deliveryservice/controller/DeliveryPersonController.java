package com.gizasystems.deliveryservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.dto.OrderDTO;
import com.gizasystems.deliveryservice.dto.UpdateAvailabilityRequest;
import com.gizasystems.deliveryservice.dto.AcceptOrderRequest;
import com.gizasystems.deliveryservice.service.DeliveryPersonService;

import com.gizasystems.deliveryservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/delivery-person")
public class DeliveryPersonController {

  @Autowired
  private DeliveryPersonService deliveryPersonService;

  @Autowired
  private OrderService orderService;

  private Long getDeliveryPersonId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long deliveryPersonId = null;
    try {
      deliveryPersonId = Long.parseLong(authentication.getName());
    } catch (Exception e) {
      return null;
    }
    return deliveryPersonId;
  }

  @PostMapping("/update-availability")
  public ResponseEntity<DeliveryPerson> updateAvailability(@RequestBody UpdateAvailabilityRequest request) {
    Long deliveryPersonId = getDeliveryPersonId();
    if (deliveryPersonId != null) {
      DeliveryPerson updatedDeliveryPerson = deliveryPersonService.updateAvailability(deliveryPersonId, request.getAvailability());
      return ResponseEntity.ok(updatedDeliveryPerson);
    }
    return ResponseEntity.badRequest().build();
  }

  // @PostMapping("/accept-order")
  // public ResponseEntity<OrderDTO> acceptOrder(@RequestBody AcceptOrderRequest request) {
  //   OrderDTO order = orderService.assignDeliveryToOrder(request.getDeliveryPersonId(), request.getOrderId());
  //   return ResponseEntity.ok(order);
  // }

  @GetMapping("/view-waiting-orders")
  public ResponseEntity<List<OrderDTO>> viewWaitingOrders() {
    List<OrderDTO> waitingOrders = orderService.getOrdersByState("WAITING_FOR_DELIVERY");
    return ResponseEntity.ok(waitingOrders);
  }

  @GetMapping("/view-assigned-orders")
  public ResponseEntity<List<OrderDTO>> viewAssignedOrders() {
    Long deliveryPersonId = getDeliveryPersonId();
    if (deliveryPersonId != null) {
      List<OrderDTO> assignedOrders = orderService.getAssignedOrders("ON_WAY", deliveryPersonId);
      return ResponseEntity.ok(assignedOrders);
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/confirm-payment/{orderId}")
  public ResponseEntity<?> confirmPayment(@PathVariable Long orderId) {
    OrderDTO order = orderService.updateOrderState(orderId, "DELIVERED");
    return ResponseEntity.ok(order);
  }

  @GetMapping("/CalculateProfit")
  public ResponseEntity<Double> CalculateProfit() {
    // TODO: this can be implemented using a single query to the database in the order-service
    Long deliveryPersonId = getDeliveryPersonId();
    if (deliveryPersonId == null) {
      return ResponseEntity.badRequest().build();
    }
    List<OrderDTO> orders = orderService.getAssignedOrders("DELIVERED", deliveryPersonId);
    double profit = 0;
    for (OrderDTO order : orders) {
      profit += order.getDeliveryFees();
    }
    return ResponseEntity.ok(profit);
  }

}
