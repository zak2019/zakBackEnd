package com.zak.application.service.api;

import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.model.NotificationType;
import java.util.Set;

public interface NotificationTypeService {

    NotificationType createNotificationType(NotificationType notificationType);
    Set<NotificationType> getAllNotificationTypes();
    NotificationType getNotificationTypeByNotificationTypeId(String notificationType);
    NotificationType getNotificationTypeByNotificationTypeCode(NotificationTypeEnum notificationTypeCode);

}
