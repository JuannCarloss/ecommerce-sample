package com.shop.ecommerce.services;

import com.shop.ecommerce.models.Address;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;


    public Address save(Address address, Customer customer){
        address.setCustomer(customer);

        return addressRepository.save(address);
    }
}
