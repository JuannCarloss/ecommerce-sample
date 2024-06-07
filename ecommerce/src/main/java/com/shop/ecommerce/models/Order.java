package com.shop.ecommerce.models;

import com.shop.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Order extends EntityID{

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem;

    public Order(Customer customer, LocalDateTime orderDate, OrderStatus status, List<OrderItem> orderItem) {
        this.customer = customer;
        this.orderDate = LocalDateTime.now();
        this.status = status;
        this.orderItem = orderItem;
    }
}
