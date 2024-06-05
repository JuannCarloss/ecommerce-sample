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
        for (int i = 0; i < list.size(); i++){
            if(productQuantityValidator(productRepository.findById(list.get(i).getProduct().getId())))
        }
        orderItemRepository.saveAll(list);
    }

    public boolean productQuantityValidator(Optional<Product> productInStock, OrderItem order){
        if (productInStock.get().getStock() < order.getProduct().getStock()){
            throw new RuntimeException("Produto indisponível em estoque");
        }
        return true;
    }
}
