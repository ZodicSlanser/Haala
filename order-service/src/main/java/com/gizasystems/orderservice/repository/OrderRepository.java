package com.gizasystems.orderservice.repository;

import com.gizasystems.orderservice.entites.Order;
import com.gizasystems.orderservice.enums.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByState(OrderState state);

    @Modifying
    @Query("UPDATE orders o SET o.state = :state WHERE o.id = :id")
    void updateState(@Param("id") Long id, @Param("state") OrderState state);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByDeliveryId(Long deliveryId);

    @Query("SELECT o FROM orders o WHERE (:restaurantId IS NULL OR o.restaurantId = :restaurantId) " +
            "AND (:state IS NULL OR o.state = :state) " +
            "AND (:customerId IS NULL OR o.customerId = :customerId) " +
            "AND (:deliveryId IS NULL OR o.deliveryId = :deliveryId)")
    List<Order> searchOrders(@Param("restaurantId") Long restaurantId,
                             @Param("state") OrderState state,
                             @Param("customerId") Long customerId,
                             @Param("deliveryId") Long deliveryId);
}
