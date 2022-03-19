package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.Set;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    Optional<NotificationEntity> findByNotificationId(String notificationId);
    Set<NotificationEntity> findByUserToNotifyAndPageConsultedFalse(UserEntity userToNotify);

    @Query("select n" +
            " from NotificationEntity n" +
            " where n.userToNotify = :user" +
            " order by  n.creationDate desc ")
    Page<NotificationEntity> getPageNotificationsByUser(@Param("user") UserEntity user,
                                                    Pageable pageable);

    @Query("select n" +
            " from NotificationEntity n" +
            " where n.userToNotify = :user" +
            " and n.consulted = false" +
            " order by  n.creationDate desc ")
    Page<NotificationEntity> getPageOfNotConsultedNotificationsByUser(@Param("user") UserEntity user,
                                                    Pageable pageable);

}
