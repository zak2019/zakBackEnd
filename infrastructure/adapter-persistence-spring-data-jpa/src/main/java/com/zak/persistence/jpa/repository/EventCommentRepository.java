package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.EventCommentEntity;
import com.zak.persistence.jpa.entity.EventEntity;
import com.zak.persistence.jpa.entity.EventWeatherEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface EventCommentRepository extends JpaRepository<EventCommentEntity, Long> {

    Set<EventCommentEntity> findByEvent(EventEntity event);
    Optional<EventCommentEntity> findByEventAndUser(EventEntity event, UserEntity user);
    Optional<EventCommentEntity> findByEventCommentId(String eventCommentId);
}
