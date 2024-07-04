package com.shop.ecommerce.services;

import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.repositories.OrderRepository;
import com.shop.ecommerce.strategy.NewOrderValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final NewOrderValidationStrategy orderValidationStrategy;

    @Transactional
    public Order post(Order entity){

        orderValidationStrategy.validate(entity);

        var save = orderRepository.save(entity);
        orderItemService.saveAll(entity.getOrderItem(), entity);

        return save;
    }
}
