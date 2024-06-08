package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product post(Product entity){
        if (entity.getStock() <= 0 ){
            throw new ValidationException("Products with quantity less than one cannot be registered");
        }
        return repository.save(entity);
    }
}
