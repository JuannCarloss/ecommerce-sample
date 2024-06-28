package com.shop.ecommerce.strategy;

import com.shop.ecommerce.models.Customer;

public interface NewCustomerValidationStrategy {

    void validate(Customer customer);
}
