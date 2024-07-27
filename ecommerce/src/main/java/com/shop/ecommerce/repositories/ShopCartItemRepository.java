package com.shop.ecommerce.repositories;

import com.shop.ecommerce.models.ShopCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCartItemRepository extends JpaRepository<ShopCartItem, Long> {

    void deleteAllByShopCartId(Long id);
    List<ShopCartItem> findAllByShopCartId(Long id);
}
