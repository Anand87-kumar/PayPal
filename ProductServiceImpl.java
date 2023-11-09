package com.anand.product.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.anand.product.model.Product;
import com.anand.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private Map<Long, Product> products = new HashMap<>();

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProductById(Long productId) {
        return products.get(productId);
    }

    @Override
    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public void updateProduct(Long productId, Product product) {
        if (products.containsKey(productId)) {
            products.put(productId, product);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        products.remove(productId);
    }
}