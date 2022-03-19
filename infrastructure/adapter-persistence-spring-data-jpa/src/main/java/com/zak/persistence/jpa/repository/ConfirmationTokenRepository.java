package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Long> {

    ConfirmationTokenEntity findByConfirmationToken(String confirmationToken);
}
