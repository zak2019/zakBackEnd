package com.zak.application.service.api;

import com.zak.domain.model.ConfirmationToken;
import com.zak.domain.model.User;

import java.util.Optional;

public interface ConfirmationTokenService {
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
    Optional<ConfirmationToken> createAndAddConfirmationToken(User user);
    boolean deleteConfirmationToken(ConfirmationToken confirmationToken);
}
