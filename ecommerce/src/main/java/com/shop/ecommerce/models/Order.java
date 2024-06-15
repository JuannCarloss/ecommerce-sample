package com.shop.ecommerce.models;

import com.shop.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@AllArgsConstructor
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

    @Column(name = "total_price")
    private Double totalPrice;

    public Order(){
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.AWAITING_PAYMENT;
        this.totalPrice = 0.0;
    }
}
