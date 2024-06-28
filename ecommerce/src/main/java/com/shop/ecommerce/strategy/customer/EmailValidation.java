package com.shop.ecommerce.strategy.customer;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.CustomerRepository;
import com.shop.ecommerce.strategy.NewCustomerValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidation implements NewCustomerValidationStrategy {

    @Autowired
    private CustomerRepository repository;
    @Override
    public void validate(Customer customer) {
        if (findEmail(customer.getEmail())){
            throw new ValidationException("Email already registered");
        }
    }

    private boolean findEmail(String email){
        return repository.findByEmail(email) != null;
    }
}
