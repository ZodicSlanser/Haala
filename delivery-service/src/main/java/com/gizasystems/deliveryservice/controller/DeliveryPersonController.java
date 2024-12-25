package com.gizasystems.deliveryservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.dto.OrderDTO;
import com.gizasystems.deliveryservice.dto.CalcFeesRequest;
import com.gizasystems.deliveryservice.dto.UpdateAvailabilityRequest;
import com.gizasystems.deliveryservice.dto.ViewAssignedOrdersRequest;
import com.gizasystems.deliveryservice.dto.AcceptOrderRequest;
import com.gizasystems.deliveryservice.service.DeliveryPersonService;

// import org.gizasystems.orderservice.entity.OrderState;
// import org.gizasystems.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/delivery-person")
public class DeliveryPersonController {

  @Autowired
  private DeliveryPersonService deliveryPersonService;

  // @Autowired
  // private OrderService orderService;

  @PostMapping("/update-availability")
  public ResponseEntity<DeliveryPerson> updateAvailability(@RequestBody UpdateAvailabilityRequest request) {
    DeliveryPerson updatedDeliveryPerson = deliveryPersonService.updateAvailability(request.getDeliveryPersonId(), request.getAvailability());
    return ResponseEntity.ok(updatedDeliveryPerson);
  }

  // @PostMapping("/accept-order")
  // public ResponseEntity<OrderDTO> acceptOrder(@RequestBody AcceptOrderRequest request) {
  //   OrderDTO order = orderService.assignDeliveryToOrder(request.getDeliveryPersonId(), request.getOrderId());
  //   return ResponseEntity.ok(order);
  // }

  // @GetMapping("/view-waiting-orders")
  // public ResponseEntity<List<OrderDTO>> viewWaitingOrders() {
  //   List<OrderDTO> waitingOrders = orderService.getOrdersByState(OrderState.WAITING_FOR_DELIVERY);
  //   return ResponseEntity.ok(waitingOrders);
  // }

  // @GetMapping("/view-assigned-orders")
  // public ResponseEntity<List<OrderDTO>> viewAssignedOrders(@RequestBody ViewAssignedOrdersRequest request) {
  //   List<OrderDTO> currentOrders = orderService.getOrdersByStateAndDeliveryID(OrderState.ON_WAY, request.getDeliveryPersonId());
  //   return ResponseEntity.ok(currentOrders);
  // }

  // @PostMapping("/confirm-payment")
  // public ResponseEntity<?> confirmPayment() {
  //   orderService.updateOrderState(OrderState.DELIVERED);
  //   return ResponseEntity.ok().build();
  // }

  // @GetMapping("/CalculateProfit")
  // public ResponseEntity<Double> CalculateProfit(@RequestBody CalcFeesRequest request) {
  //   // TODO: this can be implemented using a single query to the database in the order-service
  //   List<OrderDTO> orders = orderService.getOrdersByState(OrderState.DELIVERED, request.getDeliveryPersonId());
  //   double profit = 0;
  //   for (OrderDTO order : orders) {
  //     profit += order.getDeliveryFees();
  //   }
  //   return ResponseEntity.ok(profit);
  // }

}
