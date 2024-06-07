package com.shop.ecommerce.services;

import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product post(Product entity){
        return repository.save(entity);
    }
}
