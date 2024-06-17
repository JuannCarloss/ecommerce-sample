package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer post(Customer entity){
        if (customerRepository.findByEmail(entity.getEmail()) != null){
            throw new ValidationException("email already registered");
        }
        return customerRepository.save(entity);
    }

    public Customer update(Long id, Customer entity){
        Optional<Customer> byId = customerRepository.findById(id);

        if (byId.isPresent()){
            var updatedCustomer = byId.get();
            updatedCustomer.setFirstName(entity.getFirstName());
            updatedCustomer.setLastName(entity.getLastName());
            updatedCustomer.setEmail(entity.getEmail());
            updatedCustomer.setAddress(entity.getAddress());
            return customerRepository.save(updatedCustomer);
        }
        throw new NotFoundException("Customer not found");
    }

    public List<Customer> findAll(){
        try {
            return customerRepository.findAll();
        }catch (Exception e){
            throw new NotFoundException("Customer not found");
        }
    }

    public Optional<Customer> findById(Long id){
        try {
            return customerRepository.findById(id);
        }catch(Exception e){
            throw new NotFoundException("Customer not found");
        }
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }

}
