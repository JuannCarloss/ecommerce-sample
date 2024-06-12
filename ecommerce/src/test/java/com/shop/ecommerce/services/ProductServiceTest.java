package com.shop.ecommerce.services;

import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should save a new product in database")
    public void saveNewProduct(){
       productService.post(newProduct());
    }

    public Product newProduct(){
        return new Product("produto", "descricao", 100.0, 4);
    }

}