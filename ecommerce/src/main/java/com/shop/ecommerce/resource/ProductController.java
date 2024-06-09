package com.shop.ecommerce.resource;

import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractController{

    @Autowired
    private ProductService service;

    @PostMapping
    private ResponseEntity save(@RequestBody Product entity){
        var save = service.post(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PutMapping("{id}")
    private ResponseEntity update(@PathVariable("id") Long id, @RequestBody Product entity){
        var save = service.updateProduct(id, entity);

        return ResponseEntity.ok(save);
    }
}
