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
            throw new RuntimeException("Hey! you need to have at least one product in your shopping cart to make a purchase");
        }

        var save = orderRepository.save(entity);
        orderItemService.saveAll(entity.getOrderItem(), entity);

        return save;
    }

}
