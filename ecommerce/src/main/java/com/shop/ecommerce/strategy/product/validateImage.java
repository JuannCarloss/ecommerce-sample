package com.shop.ecommerce.strategy.product;

import com.shop.ecommerce.dtos.ProductRequestDTO;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.repositories.ProductRepository;
import com.shop.ecommerce.strategy.NewProductValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class validateImage implements NewProductValidationStrategy {

    @Autowired
    private ProductRepository repository;
    @Override
    public void validate(ProductRequestDTO data) {
        if (!existsImage(data.img())) {
            throw new ValidationException("Products must have an image");
        }
    }

    private boolean existsImage(MultipartFile data){
        return data != null;
    }
}
