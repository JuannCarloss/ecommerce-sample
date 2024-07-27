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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Should save a new customer in database")
    public void saveNewCustomer() throws IOException {
        customerService.post(newCustomer());
    }

    @Test
    @DisplayName("Should throws a new ValidationException when registering an email that already exists in database")
    public void saveNewCustomerWithEmailAlreadyRegistered() {
        Customer customer = newCustomer();
        when(customerRepository.findByEmail(anyString())).thenReturn(customer);
        assertThrows(ValidationException.class, () -> customerService.post(newCustomer()));
    }

    public Customer newCustomer(){
        return new Customer("juan", "carlos", "email", "ender");
    }

}