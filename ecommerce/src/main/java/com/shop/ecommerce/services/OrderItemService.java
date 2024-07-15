package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Order;
import com.shop.ecommerce.models.OrderItem;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final ProductService productService;

    public void saveAll(List<OrderItem> orderItemList, Order order) {
        orderItemList.forEach(this::validateStock);
        orderItemList.forEach(this::validateProductQuantity);
        orderItemList.forEach(orderItem -> orderItem.setOrder(order));
        orderItemList.forEach(list -> { totalValue(list, order);});
        orderItemRepository.saveAll(orderItemList);
    }

    public void validateStock(OrderItem orderItem) {
        Product productById = productService.getOptionalProduct(orderItem.getProduct().getId());
        if (orderItem.getProductQuantity() > productById.getStock()) {
            throw new ValidationException("We do not have stock for " + productById.getName() + " at the moment :(");
        }
        productById.setStock(productById.getStock() - orderItem.getProductQuantity());
    }
    public void totalValue(OrderItem orderItems, Order order) {
        var productById = productService.getOptionalProduct(orderItems.getProduct().getId());
        order.setTotalPrice(order.getTotalPrice() + (productById.getPrice() * orderItems.getProductQuantity()));
    }

    public void validateProductQuantity(OrderItem orderItem){
        if (orderItem.getProductQuantity() <= 0){
            var productById = productService.getOptionalProduct(orderItem.getProduct().getId());
            throw new ValidationException("You need to have at least one of " + productById.getName() + " in your shop cart");
        }
    }
}
