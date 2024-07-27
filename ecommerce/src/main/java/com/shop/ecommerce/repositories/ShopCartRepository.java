package com.shop.ecommerce.repositories;

import com.shop.ecommerce.enterprise.filter.CustomQuerydslPredicateExecutor;
import com.shop.ecommerce.models.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart, Long>, CustomQuerydslPredicateExecutor<ShopCart> {
}
