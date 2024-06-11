package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional
    public Order post(Order entity){

        if (entity.getOrderItem().isEmpty()){
            throw new ValidationException("Hey! you need to have at least one product in your shopping cart to make a purchase");
        }

        var save = orderRepository.save(entity);
        orderItemService.saveAll(entity.getOrderItem(), entity);

        return save;
    }
}
