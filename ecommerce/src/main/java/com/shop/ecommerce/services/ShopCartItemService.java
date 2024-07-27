package com.shop.ecommerce.services;

import com.shop.ecommerce.enterprise.OkNoContentException;
import com.shop.ecommerce.enums.ShopCartItemLog;
import com.shop.ecommerce.models.ShopCartItem;
import com.shop.ecommerce.models.Product;
import com.shop.ecommerce.repositories.ShopCartItemRepository;
import com.shop.ecommerce.repositories.ShopCartRepository;
import com.shop.ecommerce.strategy.NewShopCartItemsValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCartItemService {

    private final ShopCartRepository shopCartRepository;
    private final ShopCartItemRepository shopCartItemRepository;
    private final ProductService productService;
    private final NewShopCartItemsValidationStrategy shopCartItemsValidationStrategy;

    @Transactional
    public void putItemIntoShopCart(ShopCartItem shopCartItem, Long shopCartID) {
        shopCartItemsValidationStrategy.validate(shopCartItem);

        var shopCart = shopCartRepository.findById(shopCartID)
                .orElseThrow(() -> new OkNoContentException("ShopCart not found"));
        shopCartItem.setShopCart(shopCart);

        Product productById = productService.getOptionalProduct(shopCartItem.getProduct().getId());
        productById.setStock(productById.getStock() - shopCartItem.getProductQuantity());

        shopCartItemRepository.save(shopCartItem);
        updateMoveLog(shopCartItem.getId(), ShopCartItemLog.ORDER);

        shopCart.setTotalPrice(shopCart.getTotalPrice() + (productById.getPrice() * shopCartItem.getProductQuantity()));
    }

    @Transactional
    public void removeItemFromShopCart(Long shopCartItemID){

        var shopCartItem = shopCartItemRepository.findById(shopCartItemID)
                .orElseThrow(() -> new OkNoContentException("Item not Found"));

        var product = productService.getOptionalProduct(shopCartItem.getProduct().getId());
        product.setStock(product.getStock() + shopCartItem.getProductQuantity());

        updateMoveLog(shopCartItemID, ShopCartItemLog.REMOVED);
        shopCartItemRepository.save(shopCartItem);

        shopCartItemRepository.deleteById(shopCartItemID);
    }

    public void updateMoveLog(Long shopCartItemID, ShopCartItemLog shopCartItemLog){
        var shopCartItem = shopCartItemRepository.findById(shopCartItemID)
                .orElseThrow(() -> new OkNoContentException("Item not Found"));

        shopCartItem.setMoveLog(shopCartItemLog);

    }

    public List<ShopCartItem> findAllByShopCartId(Long id){
        return shopCartItemRepository.findAllByShopCartId(id);
    }


    public void deleteAllByShopCartID(Long id){
        shopCartItemRepository.deleteAllByShopCartId(id);
    }
}
