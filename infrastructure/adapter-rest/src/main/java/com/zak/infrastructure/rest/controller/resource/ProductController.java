package com.zak.infrastructure.rest.controller.resource;


import com.zak.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductController {

    @GetMapping()
    ResponseEntity<List<Product>> getProducts();

    @PostMapping()
    ResponseEntity<Void> addProduct(@RequestBody Product product);

    @DeleteMapping()
    ResponseEntity<Void> removeProduct(@RequestBody Product product);

    @GetMapping("/{productId}")
    ResponseEntity<Product> getProductById(@PathVariable Integer productId);
}
