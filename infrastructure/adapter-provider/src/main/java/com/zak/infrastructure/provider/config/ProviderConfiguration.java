package com.zak.infrastructure.provider.config;

import com.zak.infrastructure.provider.EmailService;
import com.zak.infrastructure.provider.GenerateId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderConfiguration {

    @Bean
    public GenerateId getGenerateId() {
        return new GenerateId();
    }

    @Bean
    public EmailService getEmailService() {
        return new EmailService();
    }
}
