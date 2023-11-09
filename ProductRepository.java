package com.anand.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.product.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
