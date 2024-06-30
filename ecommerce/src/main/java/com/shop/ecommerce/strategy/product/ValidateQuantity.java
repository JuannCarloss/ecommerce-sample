package com.shop.ecommerce.strategy.product;

import com.shop.ecommerce.dtos.ProductRequestDTO;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.strategy.NewProductValidationStrategy;
import org.springframework.stereotype.Component;

@Component
public class ValidateQuantity implements NewProductValidationStrategy {
    @Override
    public void validate(ProductRequestDTO data) {
        if(data.stock() <= 0){
            throw new ValidationException("Products quantity must be greater than zero");
        }
    }
}
