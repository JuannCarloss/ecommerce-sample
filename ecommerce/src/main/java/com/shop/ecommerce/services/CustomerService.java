package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private ModelMapper modelMapper;
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
            modelMapper.map(entity, updatedCustomer);
            return customerRepository.save(updatedCustomer);
        }
        throw new NotFoundException("Customer not found");
    }

    public List<Customer> findAll(){
        var list = customerRepository.findAll();

        if (list.isEmpty()){
            throw new NotFoundException("We couldn't found any customer");
        }

        return list;
    }

    public Optional<Customer> findById(Long id){
        var byid = customerRepository.findById(id);

        if (byid.isEmpty()){
            throw new NotFoundException("Customer not found");
        }

        return byid;
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }

}
