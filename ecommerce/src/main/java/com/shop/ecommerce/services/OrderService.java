package com.shop.ecommerce.services;

import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.models.OrderItem;
import com.shop.ecommerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    public Order post(Order entity){

        if (entity.getOrderItem().isEmpty()){
            throw new RuntimeException("Ei! Preencha seu carrinho para efetuar uma compra");
        }

        orderItemService.saveAll(entity.getOrderItem());

        return orderRepository.save(entity);
    }
}
