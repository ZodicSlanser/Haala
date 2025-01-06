package com.gizasystems.orderservice.entites;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gizasystems.orderservice.enums.OrderState;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(message = "Can't Create Empty Order", min = 1)
    @NotNull(message = "Can't Create Empty Order")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> items;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @PositiveOrZero
    @NotNull
    private BigDecimal subtotal;
    @PositiveOrZero
    @NotNull
    private BigDecimal deliveryFees;
    @PositiveOrZero
    @NotNull
    private BigDecimal total;

    @NotNull
    private Long customerId;
    @NotNull
    private Long restaurantId;
    private Long deliveryId;

    @FutureOrPresent
    private LocalDateTime confirmedAt;
    @FutureOrPresent
    private LocalDateTime pickupAt;
    @FutureOrPresent
    private LocalDateTime deliveredAt;
    @FutureOrPresent
    private LocalDateTime cancelledAt;

    @FutureOrPresent
    private LocalDateTime declinedAt;

    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at = LocalDateTime.now();

    public List<OrderItem> addItems(List<OrderItem> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);
        return this.items;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

