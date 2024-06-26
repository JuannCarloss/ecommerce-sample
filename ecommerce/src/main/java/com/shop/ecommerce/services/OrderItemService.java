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

    public void saveAll(List<OrderItem> orderItemList, Order order) {
        orderItemList.forEach(this::validateStock);
        orderItemList.forEach(this::validateProductQuantity);
        orderItemList.forEach(orderItem -> orderItem.setOrder(order));
        orderItemList.forEach(list -> { totalValue(list, order);});
        orderItemRepository.saveAll(orderItemList);
    }

    public boolean validateStock(OrderItem orderItem) {
        Optional<Product> byId = productRepository.findById(orderItem.getProduct().getId());
        if (orderItem.getProductQuantity() > byId.get().getStock()) {
            throw new ValidationException("We do not have stock for " + byId.get().getName() + " at the moment :(");
        }
        byId.get().setStock(byId.get().getStock() - orderItem.getProductQuantity());
        return true;
    }

    public void totalValue(OrderItem orderItems, Order order) {
            Optional<Product> byId = productRepository.findById(orderItems.getProduct().getId());
            byId.ifPresent(product -> order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * orderItems.getProductQuantity())));
    }

    public boolean validateProductQuantity(OrderItem orderItem){
        if (orderItem.getProductQuantity() <= 0){
            Optional<Product> byId = productRepository.findById(orderItem.getProduct().getId());
            throw new ValidationException("You need to have at least one of " + byId.get().getName() + " in your shop cart");
        }

        return true;
    }
}
