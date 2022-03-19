package com.zak.infrastructure.provider;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class GenericRandomIdGenerator {

    private GenericRandomIdGenerator() {
    }

    static String generateRandomId() { return encodeUderID(UUID.randomUUID());}

    private static String encodeUderID(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byteBuffer.array());
    }
}
