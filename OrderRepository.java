package com.anand.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.product.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{


}
