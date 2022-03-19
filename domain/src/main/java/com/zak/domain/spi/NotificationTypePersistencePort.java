package com.zak.domain.spi;

import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.model.NotificationType;

import java.util.Optional;
import java.util.Set;

public interface NotificationTypePersistencePort {

    NotificationType createNotificationType(NotificationType notificationType);
    Set<NotificationType> getAllNotificationTypes();
    Optional<NotificationType> getNotificationTypeByNotificationTypeId(String notificationTypeId);
    Optional<NotificationType> getNotificationTypeByNotificationTypeCode(NotificationTypeEnum notificationTypeCode);
}
