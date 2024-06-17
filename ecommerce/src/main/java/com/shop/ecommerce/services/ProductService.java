package com.shop.ecommerce.services;

import com.amazonaws.services.s3.AmazonS3;
import com.shop.ecommerce.dtos.ProductRequestDTO;
import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ProductRepository;
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
    private AmazonS3 s3Client;

    @Autowired
    private ProductRepository repository;

    public Product post(ProductRequestDTO entity){
        String url = null;

        if (entity.img() != null){
            url = uploadImg(entity.img());
        }

        if (entity.stock() <= 0 ){
            throw new ValidationException("Products with quantity less than one cannot be registered");
        }

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

    public List<Product> findAllProductsByHighestPrice(){
        try {
            return repository.findProductsWithHighestPrice();
        }catch(Exception e){
            throw new NotFoundException("We couldn't find any products");
        }
    }

    public List<Product> findAllProductsByLowestPrice(){
        try {
            return repository.findProductsWithLowestPrice();
        }catch(Exception e){
            throw new NotFoundException("We couldn't find any products");
        }
    }

    public void delete(Long id){
        try {
            repository.deleteById(id);
        }catch(Exception e){
            throw new NotFoundException("Product not found");
        }
    }

    private String uploadImg(MultipartFile multipartFile){
        String bucket = "ecommerce-products-imgs";
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try{
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucket, filename, file);
            file.delete();
            return s3Client.getUrl(bucket, filename).toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new ValidationException("Error while uploading image");
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
