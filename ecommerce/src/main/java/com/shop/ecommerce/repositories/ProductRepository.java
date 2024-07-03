package com.shop.ecommerce.repositories;

import com.shop.ecommerce.enterprise.filter.CustomQuerydslPredicateExecutor;
import com.shop.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomQuerydslPredicateExecutor<Product> {

    @Query("SELECT p FROM Product p ORDER BY price DESC")
    public List<Product> findProductsWithHighestPrice();

    @Query("SELECT p FROM Product p ORDER BY price ASC")
    public List<Product> findProductsWithLowestPrice();
}
