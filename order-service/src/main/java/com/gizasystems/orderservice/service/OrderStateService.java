package com.gizasystems.orderservice.service;

import com.gizasystems.orderservice.entites.Order;
import com.gizasystems.orderservice.enums.OrderState;
import com.gizasystems.orderservice.exceptions.InvalidOrderStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderStateService {

    private final OrderNotificationService orderNotificationService;

    public void updateOrderState(Order order, OrderState newState) {

        if (!isOrderStateValid(newState, order.getState()))
            throw new InvalidOrderStateException("Invalid order state transition");

        switch (newState) {
            case PLACED -> order.setCreated_at(LocalDateTime.now());
            case PREPARING -> order.setConfirmedAt(LocalDateTime.now());
            case WAITING_FOR_DELIVERY -> order.setPickupAt(LocalDateTime.now());
            case DELIVERED -> order.setDeliveredAt(LocalDateTime.now());
            case CANCELLED -> order.setCancelledAt(LocalDateTime.now());
            case DECLINED -> order.setDeclinedAt(LocalDateTime.now());
            default -> {
                throw new InvalidOrderStateException("Invalid order state");
            }
        }
        order.setUpdated_at(LocalDateTime.now());
        order.setState(newState);

        orderNotificationService.buildAndNotify(order, newState);

    }


    boolean isOrderStateValid(OrderState newState, OrderState currentState) {
        return switch (newState) {
            case PLACED -> currentState == null; //only valid if the order is not placed
            case PREPARING, CANCELLED, DECLINED ->
                    currentState == OrderState.PLACED; //only valid if the order is placed
            case WAITING_FOR_DELIVERY -> currentState == OrderState.PREPARING; //only valid if the order is placed
            case DELIVERED ->
                    currentState == OrderState.WAITING_FOR_DELIVERY; //only valid if the order is waiting for delivery
            default -> false;
        };
    }


}


