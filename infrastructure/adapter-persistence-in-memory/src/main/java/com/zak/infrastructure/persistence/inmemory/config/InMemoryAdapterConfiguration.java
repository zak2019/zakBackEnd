package com.zak.infrastructure.persistence.inmemory.config;

import com.zak.domain.spi.ProductPersistencePort;
import com.zak.infrastructure.persistence.inmemory.adapter.ProductInMemoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryAdapterConfiguration {

    @Bean
    public ProductPersistencePort getProductPersistencePort() {
        return new ProductInMemoryAdapter();
    }
}
