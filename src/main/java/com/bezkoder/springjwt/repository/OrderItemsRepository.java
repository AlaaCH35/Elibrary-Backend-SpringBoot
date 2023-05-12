package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
}
