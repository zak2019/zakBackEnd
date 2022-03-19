package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.NotificationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationTypeEntity, Long> {
    Optional<NotificationTypeEntity> findByNotificationTypeId(String notificationTypeId);
    Optional<NotificationTypeEntity> findByCode(String code);
}
