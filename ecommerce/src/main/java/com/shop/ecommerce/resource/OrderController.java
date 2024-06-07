package com.shop.ecommerce.resource;

import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity save(@RequestBody Order entity){
        var save = service.post(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
}
