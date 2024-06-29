package com.shop.ecommerce.strategy;

import com.shop.ecommerce.dtos.ProductRequestDTO;

public interface NewProductValidationStrategy {
    void validate(ProductRequestDTO data);
}
