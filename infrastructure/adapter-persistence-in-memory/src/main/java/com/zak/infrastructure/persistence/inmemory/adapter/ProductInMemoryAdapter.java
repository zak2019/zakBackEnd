package com.zak.infrastructure.persistence.inmemory.adapter;

import com.zak.domain.model.Product;
import com.zak.domain.spi.ProductPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInMemoryAdapter implements ProductPersistencePort {

    private static final Map<Integer, Product> productMap = new HashMap<Integer, Product>(0);

    @Override
    public void addProduct(Product product) {
        productMap.put(product.getProductId(), product);
    }

    @Override
    public void removeProduct(Product product) {
        productMap.remove(product);
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<Product>(productMap.values());
    }

    @Override
    public Product getProductById(Integer productId) {
        return productMap.get(productId);
    }
}
