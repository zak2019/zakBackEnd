package com.zak.application.service.adapter;

import com.zak.application.service.api.ConfirmationTokenService;
import com.zak.domain.model.ConfirmationToken;
import com.zak.domain.model.User;
import com.zak.domain.spi.ConfirmationTokenPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class ConfirmationTokenServiceAdapter implements ConfirmationTokenService {

    private final ConfirmationTokenPersistencePort confirmationTokenPersistencePort;

    @Autowired
    public ConfirmationTokenServiceAdapter(ConfirmationTokenPersistencePort confirmationTokenPersistencePort) {
        this.confirmationTokenPersistencePort = confirmationTokenPersistencePort;
    }
    @Override
    public Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken) {
        return confirmationTokenPersistencePort.findByConfirmationToken(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> createAndAddConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setCreatedDate(new Date());
        confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
        return confirmationTokenPersistencePort.save(confirmationToken);
    }

    @Override
    public boolean deleteConfirmationToken(ConfirmationToken confirmationToken) {
        return confirmationTokenPersistencePort.deleteConfirmationToken(confirmationToken);

    }
}
