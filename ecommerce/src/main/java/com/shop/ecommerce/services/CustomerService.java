package com.shop.ecommerce.services;

import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer post(Customer entity){
        if (customerRepository.findByEmail(entity.getEmail()) != null){
            throw new RuntimeException("email ja cadastrado");
        }
        return customerRepository.save(entity);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }

}
