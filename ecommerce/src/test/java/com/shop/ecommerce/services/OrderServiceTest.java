package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.enums.OrderStatus;
import com.shop.ecommerce.models.Customer;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.models.OrderItem;
import com.shop.ecommerce.repositories.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;


    @Test
    @DisplayName("Should throws a new ValidationException when registering an order with empty order items")
    public void throwsExceptionWhenRegisteringWithEmptyProducts(){
        Order order = new Order(newCustomer(), LocalDateTime.now(), OrderStatus.AWAITING_PAYMENT, orderItem(), 100.0);

        when(order.getOrderItem().isEmpty()).thenThrow(ValidationException.class);
    }

    public Customer newCustomer(){
        return new Customer("juan", "carlos", "email", "ender");
    }

    public List<OrderItem> orderItem(){
        return null;
    }

}