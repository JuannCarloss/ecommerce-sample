package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Should save a new custmer in databse")
    public void saveNewCustomer(){
        customerService.post(newCustomer());
    }

    @Test
    @DisplayName("Should throws a new ValidationException when registering an email that already exists in database")
    public void saveNewCustomerWithEmailAlreadyRegistered(){
        customerService.post(newCustomer());
        assertThrows(ValidationException.class, () -> customerService.post(newCustomer()));
    }

    public Customer newCustomer(){
        return new Customer("juan", "carlos", "email", "ender");
    }

}