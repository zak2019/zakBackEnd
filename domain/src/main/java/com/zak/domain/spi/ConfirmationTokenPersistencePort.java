package com.zak.domain.spi;

import com.zak.domain.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenPersistencePort {
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
    Optional<ConfirmationToken> save(ConfirmationToken confirmationToken);
    boolean deleteConfirmationToken(ConfirmationToken confirmationToken);
}
