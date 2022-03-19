package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.EventTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventTypeEntity, Long> {
    Optional<EventTypeEntity> findByEventTypeId(String eventTypeId);
    Optional<EventTypeEntity> findByCode(String code);
}
