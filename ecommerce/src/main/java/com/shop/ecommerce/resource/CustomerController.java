package com.shop.ecommerce.resource;

import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity save(@RequestBody Customer entity){
        var save = service.post(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping
    public ResponseEntity findAll(){
        List<Customer> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}
