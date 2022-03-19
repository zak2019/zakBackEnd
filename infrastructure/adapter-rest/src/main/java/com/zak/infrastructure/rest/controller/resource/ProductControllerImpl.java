package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.ProductService;
import com.zak.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/product")
public class ProductControllerImpl implements ProductController {

    private ProductService productService;

    @Autowired
    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<List<Product>>(productService.getProducts(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addProduct(Product product) {
        productService.addProduct(product);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeProduct(Product product) {
        productService.removeProduct(product);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> getProductById(Integer productId) {
        return new ResponseEntity<Product>(productService.getProductById(productId), HttpStatus.OK);
    }
}
