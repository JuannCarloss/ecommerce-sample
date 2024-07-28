package com.shop.ecommerce.dtos;

import com.shop.ecommerce.enums.OrderStatus;

public record OrderDTO(String email, String date, OrderStatus status, Double total) {
}
