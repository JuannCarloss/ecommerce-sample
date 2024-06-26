package com.shop.ecommerce.services;

import com.amazonaws.services.s3.AmazonS3;
import com.shop.ecommerce.dtos.ProductRequestDTO;
import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import com.shop.ecommerce.strategy.NewProductValidationStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private S3Service s3Service;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private List<NewProductValidationStrategy> productValidationStrategies;

    public Product post(ProductRequestDTO entity){

        productValidationStrategies.forEach(validations -> validations.validate(entity));
        String url = s3Service.uploadImg(entity.img());

        var product = Product.builder()
                .name(entity.name())
                .description(entity.description())
                .imageUrl(url)
                .price(entity.price())
                .stock(entity.stock())
                .build();

        return repository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct){
        Optional<Product> byId = repository.findById(id);

        if (byId.isPresent()){
            var product = byId.get();
            modelMapper.map(updatedProduct, product);
            return repository.save(product);
        }

        throw new NotFoundException("Product not found");
    }

    public List<Product> findAllProductsByHighestPrice(){
        var list = repository.findProductsWithHighestPrice();

        if (list.isEmpty()){
            throw new NotFoundException("We couldn't find any products");
        }

        return list;
    }

    public List<Product> findAllProductsByLowestPrice(){
        var list = repository.findProductsWithLowestPrice();

        if (list.isEmpty()){
            throw new NotFoundException("We couldn't find any products");
        }

        return list;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
