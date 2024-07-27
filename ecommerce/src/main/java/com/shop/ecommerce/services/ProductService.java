package com.shop.ecommerce.services;

import com.shop.ecommerce.dtos.ProductRequestDTO;
import com.shop.ecommerce.enterprise.OkNoContentException;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import com.shop.ecommerce.strategy.NewProductValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ModelMapper modelMapper;
    private final S3Service s3Service;
    private final ProductRepository repository;

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
        Product byId = getOptionalProduct(id);
            modelMapper.map(updatedProduct, byId);
            return repository.save(byId);
    }

    public Product getById(Long id){
        return getOptionalProduct(id);
    }

    public List<Product> findAllProductsByHighestPrice(){
        var list = repository.findProductsWithHighestPrice();

        if (list.isEmpty()){
            throw new OkNoContentException("We couldn't find any products");
        }

        return list;
    }

    public List<Product> findAllProductsByLowestPrice(){
        var list = repository.findProductsWithLowestPrice();

        if (list.isEmpty()){
            throw new OkNoContentException("We couldn't find any products");
        }

        return list;
    }

    public Page<Product> findAll(String filter, Pageable pageable){
        var list = repository.findAll(filter, Product.class, pageable);

        if (list.isEmpty()){
            throw new OkNoContentException("We couldn't find any products");
        }

        return list;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Product getOptionalProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new OkNoContentException("Product not found"));
    }
}
