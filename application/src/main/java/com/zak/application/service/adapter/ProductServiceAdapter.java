package com.zak.application.service.adapter;

import com.zak.application.service.api.ProductService;
import com.zak.domain.model.Product;
import com.zak.domain.spi.ProductPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceAdapter implements ProductService {

    private ProductPersistencePort productPersistencePort;

    @Autowired
    public ProductServiceAdapter(ProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void addProduct(Product product) {
        productPersistencePort.addProduct(product);
    }

    @Override
    public void removeProduct(Product product) {
        productPersistencePort.removeProduct(product);
    }

    @Override
    public List<Product> getProducts() {
        return productPersistencePort.getProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productPersistencePort.getProductById(productId);
    }
}
