package com.zak.application.service.adapter;

import com.zak.application.service.api.*;
import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.exception.*;
import com.zak.domain.model.*;
import com.zak.domain.model.notification.NotificationContainer;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.NotificationPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class NotificationServiceAdapter implements NotificationService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private final NotificationPersistencePort notificationPersistencePort;
    private final NotificationTypeService notificationTypeService;
    private final UserService userService;

    @Autowired
    private IdGererator idGererator;

    public NotificationServiceAdapter(NotificationPersistencePort notificationPersistencePort,
                                      NotificationTypeService notificationTypeService,
                                      UserService userService) {
        this.notificationPersistencePort = notificationPersistencePort;
        this.notificationTypeService = notificationTypeService;
        this.userService = userService;
    }


    @Override
    public Notification createNotification(Notification notification) {
        notification.setCreationDate(new Date());
        notification.setNotificationId(idGererator.generateUniqueId());
        return notificationPersistencePort.saveNotification(notification);
    }

    @Override
    public Notification getNotificationByNotificationId(String notificationId) {
        Optional<Notification> notification = notificationPersistencePort.findByNotificationId(notificationId);
        if (!notification.isPresent()) {
            throw new NotificationException();
        }
        return notification.get();
    }

    @Override
    public Notification setNotificationConsulted(String notificationId) {
        Notification notif = getNotificationByNotificationId(notificationId);
        notif.setConsulted(true);
        return notificationPersistencePort.saveNotification(notif);
    }

    @Override
    public boolean setNotificationPageConsulted(String userId) {
        Set<Notification> allNotPageConsultedNotificationsByUserId = getAllNotPageConsultedNotificationsByUserId(userId);
        allNotPageConsultedNotificationsByUserId.stream().forEach( notification -> {
            notification.setPageConsulted(true);
            notificationPersistencePort.saveNotification(notification);
        });
        return true;
    }

    @Override
    public void sendNotificationToUser(Notification notification, NotificationTypeEnum type) {
        NotificationType notificationType = notificationTypeService.getNotificationTypeByNotificationTypeCode(type);

        String userId = notification.getUserToNotify().getUserId();
        notification.setNotificationType(notificationType);
        Notification savedNotification = createNotification(notification);

        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.EVENT_STARTED);
        notificationContainer.setHeaderNotification(savedNotification);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(userId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSend("/queue/notify/" + userId, notificationContainer, headerAccessor.getMessageHeaders());
    }

    @Override
    public PageGenerics<Notification> getNotificationsByUserId(String userId, int page, int size) {
        User user = getAndCheckUser(userId);
        return notificationPersistencePort.getNotificationsByUser(user, page, size);
    }

    @Override
    public Set<Notification> getAllNotPageConsultedNotificationsByUserId(String userId) {
        User user = getAndCheckUser(userId);
        return notificationPersistencePort.getAllNotPageConsultedNotificationsByUser(user);
    }

    @Override
    public PageGenerics<Notification> getNotConsultedNotificationsByUserId(String userId, int page, int size) {
        User user = getAndCheckUser(userId);
        return notificationPersistencePort.getNotConsultedNotificationsByUser(user, page, size);
    }

    private User getAndCheckUser(String userId) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Override
    public void sendSprintWeatherNotificationToUser(String userToNotifyId, RetroSprintWeather retroSprintWeather) {

        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_WEATHER_UPDATED);
        notificationContainer.setRetroSprintWeather(retroSprintWeather);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintNotificationToUser(String userToNotifyId, RetroSprint retroSprint) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_STATUS_UPDATED);
        notificationContainer.setRetroSprint(retroSprint);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentGroupUpdatedNotificationToUser(String userToNotifyId, RetroSprintCommentGroup retroSprintCommentGroup) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_GROUP_UPDATED);
        notificationContainer.setRetroSprintCommentGroup(retroSprintCommentGroup);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentGroupAddedNotificationToUser(String userToNotifyId, RetroSprintCommentGroup retroSprintCommentGroup) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_GROUP_ADDED);
        notificationContainer.setRetroSprintCommentGroup(retroSprintCommentGroup);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentGroupDeletedNotificationToUser(String userToNotifyId, RetroSprintCommentGroup retroSprintCommentGroup) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_GROUP_DELETED);
        notificationContainer.setRetroSprintCommentGroup(retroSprintCommentGroup);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentGroupVoteStartedUpdatedNotificationToUser(String userToNotifyId, Set<RetroSprintCommentGroup> retroSprintCommentGroups) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_GROUPS_VOTE_STARTED_UPDATED);
        notificationContainer.setRetroSprintCommentGroups(retroSprintCommentGroups);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentGroupVoteAddedNotificationToUser(String userToNotifyId, RetroSprintCommentGroupVote retroSprintCommentGroupVote) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_GROUP_VOTE_ADDED);
        notificationContainer.setRetroSprintCommentGroupVote(retroSprintCommentGroupVote);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentGroupVoteDeletedNotificationToUser(String userToNotifyId, RetroSprintCommentGroupVote retroSprintCommentGroupVote) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_GROUP_VOTE_DELETED);
        notificationContainer.setRetroSprintCommentGroupVote(retroSprintCommentGroupVote);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentUpdatedNotificationToUser(String userToNotifyId, RetroSprintComment retroSprintComment) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_UPDATED);
        notificationContainer.setRetroSprintComment(retroSprintComment);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentAddedNotificationToUser(String userToNotifyId, RetroSprintComment retroSprintComment) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_ADDED);
        notificationContainer.setRetroSprintComment(retroSprintComment);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentDeletedNotificationToUser(String userToNotifyId, Set<RetroSprintComment> retroSprintUpdatedComments) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_DELETED);
        notificationContainer.setRetroSprintUpdatedComments(retroSprintUpdatedComments);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentDragDroppedInSameGroupNotificationToUser(String userToNotifyId,
                                                                               DragDropRetroSprintComment dragDropRetroSprintComment) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_DRAG_DROPPED_IN_SAME_GROUP);
        notificationContainer.setDragDropRetroSprintComment(dragDropRetroSprintComment);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentDragDroppedInOtherGroupNotificationToUser(String userToNotifyId,
                                                                               DragDropRetroSprintComment dragDropRetroSprintComment) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_DRAG_DROPPED_IN_OTHER_GROUP);
        notificationContainer.setDragDropRetroSprintComment(dragDropRetroSprintComment);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentVoteAddedNotificationToUser(String userToNotifyId, RetroSprintCommentVote retroSprintCommentVote) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_VOTE_ADDED);
        notificationContainer.setRetroSprintCommentVote(retroSprintCommentVote);

        sendNotification(userToNotifyId, notificationContainer);
    }

    @Override
    public void sendRetroSprintCommentVoteDeletedNotificationToUser(String userToNotifyId, RetroSprintCommentVote retroSprintCommentVote) {
        NotificationContainer notificationContainer = new NotificationContainer();
        notificationContainer.setNotificationType(NotificationTypeEnum.RETRO_SPRINT_COMMENT_VOTE_DELETED);
        notificationContainer.setRetroSprintCommentVote(retroSprintCommentVote);

        sendNotification(userToNotifyId, notificationContainer);
    }

    private void sendNotification(String userToNotifyId, NotificationContainer notificationContainer) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(userToNotifyId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSend("/queue/notify/" + userToNotifyId,  notificationContainer, headerAccessor.getMessageHeaders());
    }

}
