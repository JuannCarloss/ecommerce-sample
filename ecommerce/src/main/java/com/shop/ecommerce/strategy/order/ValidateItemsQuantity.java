package com.shop.ecommerce.strategy.order;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.strategy.NewOrderValidationStrategy;

public class ValidateItemsQuantity implements NewOrderValidationStrategy {
    @Override
    public void validate(Order order) {
        if (order.getOrderItem().isEmpty()){
            throw new ValidationException("Hey! you need to have at least one product in your shopping cart to make a purchase");
        }
    }
}
