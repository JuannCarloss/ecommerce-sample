package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.NotFoundException;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.repositories.OrderRepository;
import com.shop.ecommerce.strategy.NewOrderValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private NewOrderValidationStrategy orderValidationStrategy;

    @Transactional
    public Order post(Order entity){

        orderValidationStrategy.validate(entity);

        var save = orderRepository.save(entity);
        orderItemService.saveAll(entity.getOrderItem(), entity);

        return save;
    }
}
