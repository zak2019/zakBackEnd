package com.zak.infrastructure.provider;

import com.zak.domain.spi.IdGererator;

public class GenerateId implements IdGererator {

    @Override
    public String generateUniqueId() {
        String randomUniqueId = GenericRandomIdGenerator.generateRandomId();

        return randomUniqueId;
    }
}
