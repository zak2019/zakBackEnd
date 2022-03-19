package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.ConfirmationToken;
import com.zak.domain.spi.ConfirmationTokenPersistencePort;
import com.zak.persistence.jpa.entity.ConfirmationTokenEntity;
import com.zak.persistence.jpa.repository.ConfirmationTokenRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConfirmationTokenAdapter implements ConfirmationTokenPersistencePort {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public ConfirmationTokenAdapter(ConfirmationTokenRepository confirmationTokenRepository,
                                    MapperFacade orikaMapperFacade) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @Override
    public Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken) {
        ConfirmationTokenEntity token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token == null) {
            throw new RuntimeException("The link is invalid or expired!");
        }
        return Optional.of(
                orikaMapperFacade.map(
                        token,
                        ConfirmationToken.class)
        );
    }

    @Override
    public Optional<ConfirmationToken> save(ConfirmationToken confirmationToken) {
        ConfirmationTokenEntity token = confirmationTokenRepository.save(
                orikaMapperFacade.map(
                confirmationToken,
                ConfirmationTokenEntity.class));
        if(token == null) {
            throw new RuntimeException("The link is invalid or expired!");
        }
        return Optional.of(
                orikaMapperFacade.map(
                        token,
                        ConfirmationToken.class)
        );
    }

    @Override
    public boolean deleteConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(orikaMapperFacade.map(confirmationToken, ConfirmationTokenEntity.class));
        return true;
    }
}
