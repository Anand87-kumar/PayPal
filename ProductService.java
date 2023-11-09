package com.anand.product.service;



import java.util.List;

import com.anand.product.model.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void addProduct(Product product);
    void updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
}
