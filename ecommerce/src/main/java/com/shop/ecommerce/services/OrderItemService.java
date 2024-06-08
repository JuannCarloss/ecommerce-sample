package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.models.OrderItem;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.OrderItemRepository;
import com.shop.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public void saveAll(List<OrderItem> list, Order order){

        list.forEach(this::validateStock);
        list.forEach(orderItem -> orderItem.setOrder(order));
        orderItemRepository.saveAll(list);
    }

    public boolean validateStock(OrderItem orderItem){
        Optional<Product> byId = productRepository.findById(orderItem.getProduct().getId());
        if (orderItem.getProductQuantity() > byId.get().getStock()){
            throw new ValidationException("We do not have stock of this product at the moment :(");
        }
        byId.get().setStock(byId.get().getStock() - orderItem.getProductQuantity());
        return true;
    }

}
