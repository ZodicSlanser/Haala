package com.gizasystems.deliveryservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.dto.OrderDTO;
import com.gizasystems.deliveryservice.service.DeliveryPersonService;
import com.gizasystems.deliveryservice.service.OrderService;

@RestController
@RequestMapping("/delivery-person")
public class DeliveryPersonController {
  @Autowired
  private DeliveryPersonService deliveryPersonService;

  @Autowired
  private OrderService orderService;

  // TODO: think of a better way to get rid of the duplicate code
  // used later in each method to get the delivery person id from the request
  private Long getDeliveryPersonId(HttpServletRequest request) {
    try {
      String userIdHeader = request.getHeader("X-User-Id");
      if (userIdHeader != null && !userIdHeader.isEmpty()) {
        return Long.parseLong(userIdHeader);
      }
    } catch (Exception e) { }
    return null;
  }

  @PostMapping("/availability")
  public ResponseEntity<DeliveryPerson> updateAvailability(HttpServletRequest request, @RequestParam("available") boolean available) {
      Long deliveryPersonId = getDeliveryPersonId(request);
      if (deliveryPersonId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      DeliveryPerson updatedDeliveryPerson = deliveryPersonService.updateAvailability(
          deliveryPersonId, available);
      return ResponseEntity.ok(updatedDeliveryPerson);
  }

  @PutMapping("/orders/{id}/accept")
  public ResponseEntity<OrderDTO> acceptOrder(HttpServletRequest request, @PathVariable Long id) {
    Long deliveryPersonId = getDeliveryPersonId(request);
    if (deliveryPersonId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    OrderDTO order = orderService.assignDeliveryToOrder(id, deliveryPersonId);
    return ResponseEntity.ok(order);
  }

  @GetMapping("/orders/waiting")
  public ResponseEntity<List<OrderDTO>> viewWaitingOrders(HttpServletRequest request) {
    Long deliveryPersonId = getDeliveryPersonId(request);
    if (deliveryPersonId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<OrderDTO> waitingOrders = orderService.getOrdersByState("WAITING_FOR_DELIVERY");
    return ResponseEntity.ok(waitingOrders);
  }

  @GetMapping("/orders/assigned")
  public ResponseEntity<List<OrderDTO>> viewAssignedOrders(HttpServletRequest request) {
    Long deliveryPersonId = getDeliveryPersonId(request);
    if (deliveryPersonId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<OrderDTO> assignedOrders = orderService.getAssignedOrders("ON_WAY", deliveryPersonId);
    return ResponseEntity.ok(assignedOrders);
  }

  @PostMapping("/orders/{orderId}/confirm-payment")
  public ResponseEntity<?> confirmPayment(HttpServletRequest request, @PathVariable Long orderId) {
    Long deliveryPersonId = getDeliveryPersonId(request);
    if (deliveryPersonId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    OrderDTO order = orderService.updateOrderState(orderId, "DELIVERED");
    return ResponseEntity.ok(order);
  }

  @GetMapping("/profit")
  public ResponseEntity<Double> calculateProfit(HttpServletRequest request) {
    // TODO: this can be implemented using a single query to the database in the order-service
    Long deliveryPersonId = getDeliveryPersonId(request);
    if (deliveryPersonId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<OrderDTO> orders = orderService.getAssignedOrders("DELIVERED", deliveryPersonId);
    double profit = 0;
    for (OrderDTO order : orders) {
      profit += order.getDeliveryFees();
    }
    return ResponseEntity.ok(profit);
  }

}
