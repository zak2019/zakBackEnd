package com.zak.application.service.adapter;

import com.zak.application.service.api.NotificationTypeService;
import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.exception.NotificationTypeException;
import com.zak.domain.model.NotificationType;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.NotificationTypePersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class NotificationTypeServiceAdapter implements NotificationTypeService {

    private final NotificationTypePersistencePort notificationTypePersistencePort;

    @Autowired
    private IdGererator idGererator;

    @Autowired
    public NotificationTypeServiceAdapter(NotificationTypePersistencePort notificationTypePersistencePort) {
        this.notificationTypePersistencePort = notificationTypePersistencePort;
    }

    @Override
    public NotificationType createNotificationType(NotificationType notificationType) {
        notificationType.setNotificationTypeId(idGererator.generateUniqueId());
        return notificationTypePersistencePort.createNotificationType(notificationType);
    }

    @Override
    public Set<NotificationType> getAllNotificationTypes() {
        return notificationTypePersistencePort.getAllNotificationTypes();
    }

    @Override
    public NotificationType getNotificationTypeByNotificationTypeId(String notificationTypeId) {
        Optional<NotificationType> type = notificationTypePersistencePort.getNotificationTypeByNotificationTypeId(notificationTypeId);
        if (!type.isPresent()) {
            throw new NotificationTypeException();
        }
        return type.get();
    }

    @Override
    public NotificationType getNotificationTypeByNotificationTypeCode(NotificationTypeEnum notificationTypeCode) {
        Optional<NotificationType> type = notificationTypePersistencePort.getNotificationTypeByNotificationTypeCode(notificationTypeCode);
        if (!type.isPresent()) {
            throw new NotificationTypeException();
        }
        return type.get();
    }
}
