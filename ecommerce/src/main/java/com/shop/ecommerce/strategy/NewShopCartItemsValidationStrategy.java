package com.shop.ecommerce.strategy;

import com.shop.ecommerce.models.ShopCartItem;

public interface NewShopCartItemsValidationStrategy {

    void validate(ShopCartItem shopCartItem);
}
