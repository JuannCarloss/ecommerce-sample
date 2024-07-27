package com.shop.ecommerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.ecommerce.enums.ShopCartItemLog;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity(name = "shopcart_itens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE shopcart_itens SET deleted_at = now() WHERE id=?")
@SQLRestriction("deleted_at is null")
public class ShopCartItem extends EntityID{

    @ManyToOne
    @JoinColumn(name = "shopcart_id")
    @JsonIgnore
    private ShopCart shopCart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "deleted_at")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Column(name = "move_log")
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ShopCartItemLog moveLog;
}
