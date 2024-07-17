package com.shop.ecommerce.dtos;

public record OrderToPaymentDTO(Long orderID, String userEmail, Double amount) {
}
