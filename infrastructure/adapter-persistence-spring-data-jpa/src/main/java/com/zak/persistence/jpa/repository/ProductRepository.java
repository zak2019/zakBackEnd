package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    ProductEntity findByProductId(Integer productId);
}
