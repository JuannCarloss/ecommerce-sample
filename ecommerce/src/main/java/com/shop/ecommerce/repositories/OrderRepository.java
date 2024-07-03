package com.shop.ecommerce.repositories;

import com.shop.ecommerce.enterprise.filter.CustomQuerydslPredicateExecutor;
import com.shop.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CustomQuerydslPredicateExecutor<Order> {
}
