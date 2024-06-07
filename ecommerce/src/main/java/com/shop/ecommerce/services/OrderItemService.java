package com.shop.ecommerce.services;

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

    public void saveAll(List<OrderItem> list){
        for (OrderItem orderItem : list) {
            if (productQuantityValidator(productRepository.findById(orderItem.getProduct().getId()), orderItem)){
                throw new RuntimeException("We don't have this product in stock at the moment :(");
            }
        }
        orderItemRepository.saveAll(list);
    }

    public boolean productQuantityValidator(Optional<Product> productInStock, OrderItem order){
        return productInStock.filter(product -> product.getStock() < order.getProduct().getStock()).isPresent();
    }
}
