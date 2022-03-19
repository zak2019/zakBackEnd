package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.*;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.EventPersistencePort;
import com.zak.domain.spi.NotificationPersistencePort;
import com.zak.persistence.jpa.entity.*;
import com.zak.persistence.jpa.repository.EventRepository;
import com.zak.persistence.jpa.repository.NotificationRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class NotificationAdapter implements NotificationPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationAdapter(MapperFacade orikaMapperFacade,
                               NotificationRepository notificationRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification saveNotification(Notification notification) {
        NotificationEntity save = notificationRepository.save(orikaMapperFacade.map(notification, NotificationEntity.class));
        return orikaMapperFacade.map(save, Notification.class);
    }

    @Override
    public Optional<Notification> findByNotificationId(String notificationId) {
        return Optional.of(orikaMapperFacade.map(notificationRepository.findByNotificationId(notificationId).get(), Notification.class));
    }

    @Override
    public Set<Notification> getAllNotPageConsultedNotificationsByUser(User user) {
        UserEntity entity = orikaMapperFacade.map(user, UserEntity.class);
        return orikaMapperFacade.mapAsSet(
                notificationRepository.findByUserToNotifyAndPageConsultedFalse(entity), Notification.class);
    }

    @Override
    public PageGenerics<Notification> getNotificationsByUser(User user, int page, int size) {
        Page<NotificationEntity> usersNotification = notificationRepository.getPageNotificationsByUser(
                orikaMapperFacade.map(user, UserEntity.class),
                PageRequest.of(page, size));

        Set<Notification> notifications = orikaMapperFacade.mapAsSet(
                usersNotification.getContent(),
                Notification.class);

        PageGenerics<Notification> pageOfNotifications = new PageGenerics<>();
        pageOfNotifications.setData(notifications);
        pageOfNotifications.setSize(usersNotification.getNumberOfElements());
        pageOfNotifications.setActualPage(usersNotification.getNumber());
        pageOfNotifications.setTotalPages(usersNotification.getTotalPages());
        pageOfNotifications.setTotalData((int) usersNotification.getTotalElements());
        return pageOfNotifications;
    }

    @Override
    public PageGenerics<Notification> getNotConsultedNotificationsByUser(User user, int page, int size) {
        Page<NotificationEntity> usersNotification = notificationRepository.getPageOfNotConsultedNotificationsByUser(
                orikaMapperFacade.map(user, UserEntity.class),
                PageRequest.of(page, size));

        Set<Notification> notifications = orikaMapperFacade.mapAsSet(
                usersNotification.getContent(),
                Notification.class);

        PageGenerics<Notification> pageOfNotifications = new PageGenerics<>();
        pageOfNotifications.setData(notifications);
        pageOfNotifications.setSize(usersNotification.getNumberOfElements());
        pageOfNotifications.setActualPage(usersNotification.getNumber());
        pageOfNotifications.setTotalPages(usersNotification.getTotalPages());
        pageOfNotifications.setTotalData((int) usersNotification.getTotalElements());
        return pageOfNotifications;
    }
}
