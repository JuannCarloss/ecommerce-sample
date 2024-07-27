package com.shop.ecommerce.strategy.shopCartItem;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.models.ShopCartItem;
import com.shop.ecommerce.services.ProductService;
import com.shop.ecommerce.strategy.NewShopCartItemsValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateShopCartItemQuantity implements NewShopCartItemsValidationStrategy {

    @Autowired
    private ProductService productService;

    @Override
    public void validate(ShopCartItem shopCartItem) {
        Product productById = productService.getOptionalProduct(shopCartItem.getProduct().getId());
        if (shopCartItem.getProductQuantity() > productById.getStock()) {
            throw new ValidationException("We do not have stock for " + productById.getName() + " at the moment :(");
        }
    }
}
