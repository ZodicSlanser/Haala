package com.gizasystems.orderservice.service;

import com.gizasystems.orderservice.DTOs.ItemDTO;
import com.gizasystems.orderservice.DTOs.OrderSearchCriteria;
import com.gizasystems.orderservice.DTOs.PlaceOrderDTO;
import com.gizasystems.orderservice.entites.Order;
import com.gizasystems.orderservice.entites.OrderItem;
import com.gizasystems.orderservice.enums.OrderState;
import com.gizasystems.orderservice.exceptions.OrderNotFoundException;
import com.gizasystems.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStateService orderStateService;

    public OrderService(OrderRepository orderRepository, OrderStateService orderStateService) {
        this.orderRepository = orderRepository;
        this.orderStateService = orderStateService;
    }

    public Order createOrder(PlaceOrderDTO placeOrderDTO) {
        BigDecimal subTotal = calculateSubTotalPrice(placeOrderDTO.items());
        BigDecimal deliveryCost = BigDecimal.valueOf(15.0);
        List<OrderItem> orderItems = convertItemsToOrderItems(placeOrderDTO.items());
        Order order = Order.builder()
                .customerId(placeOrderDTO.userId())
                .restaurantId(placeOrderDTO.restaurantId())
                .state(OrderState.PLACED)
                .subtotal(subTotal)
                .deliveryFees(deliveryCost)
                .total(subTotal.add(deliveryCost))
                .confirmedAt(LocalDateTime.now())
                .build();
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.setItems(orderItems);

        System.out.println("Order: " + order);
        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }

    public List<Order> searchOrders(OrderSearchCriteria criteria, Long userId, String userRole) {

        if (userId != null) {
            if (userRole.equals("ADMIN")) {
                return orderRepository.searchOrders(criteria.restaurantId(), criteria.state(),
                        criteria.customerId(), criteria.deliveryId());
            }

            if (userRole.equals("CUSTOMER")) {
                criteria = new OrderSearchCriteria(criteria.restaurantId(), criteria.state(),
                        userId, criteria.deliveryId());
            }

            if (userRole.equals("DELIVERY")) {
                criteria = new OrderSearchCriteria(criteria.restaurantId(), criteria.state(),
                        criteria.customerId(), userId);
            }
        }

        return orderRepository.searchOrders(criteria.restaurantId(), criteria.state(),
                criteria.customerId(), criteria.deliveryId());
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderState(Long orderId, OrderState newState) {
        Order order = getOrder(orderId);
        orderStateService.updateOrderState(order, newState);
        return updateOrder(order);
    }

    public Order assignDeliveryPerson(Long orderId, Long deliveryId) {
        Order order = getOrder(orderId);
        if (order.getDeliveryId() != null) {
            throw new IllegalStateException("Order already has a delivery person assigned");
        }
        order.setDeliveryId(deliveryId);
        orderStateService.updateOrderState(order, OrderState.WAITING_FOR_DELIVERY);
        return updateOrder(order);
    }

    private BigDecimal calculateSubTotalPrice(List<ItemDTO> items) {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> convertItemsToOrderItems(List<ItemDTO> items) {
        return items.stream()
                .map(item -> OrderItem.builder()
                        .itemId(item.id())
                        .price(item.price())
                        .quantity(item.quantity())
                        .category(item.category())
                        .comments(item.comments())
                        .build())
                .collect(Collectors.toList());
    }
}
