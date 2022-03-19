package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.EventEntity;
import com.zak.persistence.jpa.entity.EventWeatherEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface EventWeatherRepository extends JpaRepository<EventWeatherEntity, Long> {
    Set<EventWeatherEntity> findByEvent(EventEntity event);
    Optional<EventWeatherEntity> findByEventAndUser(EventEntity event, UserEntity user);
}
