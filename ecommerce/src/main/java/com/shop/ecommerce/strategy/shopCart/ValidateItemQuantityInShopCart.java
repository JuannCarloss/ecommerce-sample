package com.shop.ecommerce.strategy.shopCart;

import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.models.ShopCart;
import com.shop.ecommerce.repositories.ShopCartItemRepository;
import com.shop.ecommerce.strategy.NewShopCartValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateItemQuantityInShopCart implements NewShopCartValidationStrategy {

    @Autowired
    private ShopCartItemRepository shopCartItemRepository;

    @Override
    public void validate(ShopCart shopCart) {
        var list = shopCartItemRepository.findAllByShopCartId(shopCart.getId());

        if (list.isEmpty()){
            throw new ValidationException("You must have at least one product in your shopcart!!!");
        }
    }
}
