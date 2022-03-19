package com.zak.application.service.api;

import com.zak.domain.model.Product;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    void removeProduct(Product product);

    List<Product> getProducts();

    Product getProductById(Integer productId);
}
