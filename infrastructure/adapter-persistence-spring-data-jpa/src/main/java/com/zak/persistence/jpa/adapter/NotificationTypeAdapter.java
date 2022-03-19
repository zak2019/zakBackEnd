package com.zak.persistence.jpa.adapter;

import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.model.EventType;
import com.zak.domain.model.NotificationType;
import com.zak.domain.spi.NotificationTypePersistencePort;
import com.zak.persistence.jpa.entity.NotificationTypeEntity;
import com.zak.persistence.jpa.repository.NotificationTypeRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class NotificationTypeAdapter implements NotificationTypePersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final NotificationTypeRepository notificationTypeRepository;

    @Autowired
    public NotificationTypeAdapter(MapperFacade orikaMapperFacade,
                                   NotificationTypeRepository notificationTypeRepository) {
        this.orikaMapperFacade = orikaMapperFacade;
        this.notificationTypeRepository = notificationTypeRepository;
    }

    @Override
    public NotificationType createNotificationType(NotificationType notificationType) {
        NotificationTypeEntity notificationTypeEntity = orikaMapperFacade.map(notificationType, NotificationTypeEntity.class);
        return orikaMapperFacade.map(notificationTypeRepository.save(notificationTypeEntity), NotificationType.class);
    }

    @Override
    public Set<NotificationType> getAllNotificationTypes() {
        return orikaMapperFacade.mapAsSet(notificationTypeRepository.findAll(), NotificationType.class);
    }

    @Override
    public Optional<NotificationType> getNotificationTypeByNotificationTypeId(String notificationTypeId) {
        return Optional.of(orikaMapperFacade.map(notificationTypeRepository
                .findByNotificationTypeId(notificationTypeId).get(), NotificationType.class));
    }

    @Override
    public Optional<NotificationType> getNotificationTypeByNotificationTypeCode(NotificationTypeEnum notificationTypeCode) {
        return Optional.of(orikaMapperFacade.map(notificationTypeRepository
                .findByCode(notificationTypeCode.toString()).get(), NotificationType.class));
    }
}
