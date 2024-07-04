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

    public void validateStock(OrderItem orderItem) {
        var productById = getOptionalProduct(orderItem.getProduct().getId());
        if (orderItem.getProductQuantity() > productById.get().getStock()) {
            throw new ValidationException("We do not have stock for " + productById.get().getName() + " at the moment :(");
        }
        productById.get().setStock(productById.get().getStock() - orderItem.getProductQuantity());
    }
    public void totalValue(OrderItem orderItems, Order order) {
        var productById = getOptionalProduct(orderItems.getProduct().getId());
        productById.ifPresent(product -> order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * orderItems.getProductQuantity())));
    }

    public void validateProductQuantity(OrderItem orderItem){
        if (orderItem.getProductQuantity() <= 0){
            var productById = getOptionalProduct(orderItem.getProduct().getId());
            throw new ValidationException("You need to have at least one of " + productById.get().getName() + " in your shop cart");
        }
    }
    private Optional<Product> getOptionalProduct(Long id) {
        return productRepository.findById(id);
    }
}
