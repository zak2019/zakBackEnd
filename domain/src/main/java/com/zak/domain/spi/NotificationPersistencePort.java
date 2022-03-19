package com.zak.domain.spi;

import com.zak.domain.model.*;
import com.zak.domain.model.util.PageGenerics;

import java.util.Optional;
import java.util.Set;

public interface NotificationPersistencePort {

    Notification saveNotification(Notification notification);
    Optional<Notification> findByNotificationId(String notificationId);
    Set<Notification> getAllNotPageConsultedNotificationsByUser(User user);
    PageGenerics<Notification> getNotificationsByUser(User user, int page, int size);
    PageGenerics<Notification> getNotConsultedNotificationsByUser(User user, int page, int size);
}
