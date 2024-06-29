package com.shop.ecommerce.strategy;

import com.shop.ecommerce.models.Order;

public interface NewOrderValidationStrategy {

    void validate(Order order);
}
