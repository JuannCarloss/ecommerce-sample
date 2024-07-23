package com.shop.ecommerce.services;

import com.amazonaws.services.kms.model.NotFoundException;
import com.shop.ecommerce.client.SendEmail;
import com.shop.ecommerce.dtos.EmailRequestDTO;
import com.shop.ecommerce.enterprise.OkNoContentException;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.CustomerRepository;
import com.shop.ecommerce.strategy.NewCustomerValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final NewCustomerValidationStrategy customerValidationStrategy;
    private final SendEmail sendEmail;

    @Transactional
    public Customer post(Customer entity) throws IOException {
        customerValidationStrategy.validate(entity);
        sendEmail.send(new EmailRequestDTO(entity.getEmail(), "REGISTER"));
        addressService.save(entity.getAddress(), entity);
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
            throw new OkNoContentException("We couldn't found any customer");
        }

        return list;
    }

    public Customer findById(Long id){
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }

}
