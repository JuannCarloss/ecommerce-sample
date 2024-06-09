package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Product updateProduct(Long id, Product updatedProduct){
        Optional<Product> byId = repository.findById(id);

        if (byId.isPresent()){

            var product = Product.builder()
                    .name(updatedProduct.getName())
                    .price(updatedProduct.getPrice())
                    .description(updatedProduct.getDescription())
                    .stock(updatedProduct.getStock())
                    .build();
            product.setId(byId.get().getId());
            return repository.save(product);
        }

        throw new NotFoundException("Product not found");
    }
}
