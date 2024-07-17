package com.shop.ecommerce.services;

import com.shop.ecommerce.dtos.OrderToPaymentDTO;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.repositories.CustomerRepository;
import com.shop.ecommerce.repositories.OrderRepository;
import com.shop.ecommerce.strategy.NewOrderValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final NewOrderValidationStrategy orderValidationStrategy;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CustomerRepository customerRepository;
    private static final String TOPIC = "order-payment";

    @Transactional
    public Order post(Order entity){

        orderValidationStrategy.validate(entity);

        var order = orderRepository.save(entity);
        orderItemService.saveAll(entity.getOrderItem(), entity);

        Optional<Customer> byID = customerRepository.findById(order.getCustomer().getId());

        var payment = new OrderToPaymentDTO(order.getId(), byID.get().getEmail(), order.getTotalPrice());

        kafkaTemplate.send(TOPIC, payment);

        return order;
    }
}
