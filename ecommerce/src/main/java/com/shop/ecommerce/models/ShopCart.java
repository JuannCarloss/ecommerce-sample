package com.shop.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "shopcart")
@Getter
@Setter
@AllArgsConstructor
public class ShopCart extends EntityID{

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "total_price")
    private Double totalPrice;

    public ShopCart() {
    }

    public ShopCart(Customer customer) {
        this.customer = customer;
        this.totalPrice = 0.0;
    }
}
