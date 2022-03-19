package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintWeatherRepository extends JpaRepository<RetroSprintWeatherEntity, Long> {
    Set<RetroSprintWeatherEntity> findByRetroSprint(RetroSprintEntity retroSprintEntity);
    Optional<RetroSprintWeatherEntity> findByRetroSprintAndUser(RetroSprintEntity retroSprintEntity, UserEntity user);
}
