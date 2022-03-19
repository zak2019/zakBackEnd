package com.zak.application.service.api;

import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.model.*;
import com.zak.domain.model.util.PageGenerics;

import java.util.Set;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Notification getNotificationByNotificationId(String notificationId);
    Notification setNotificationConsulted(String notificationId);
    boolean setNotificationPageConsulted(String userId);
    Set<Notification> getAllNotPageConsultedNotificationsByUserId(String userId);
    PageGenerics<Notification>  getNotificationsByUserId(String userId, int page, int size);
    PageGenerics<Notification>  getNotConsultedNotificationsByUserId(String userId, int page, int size);
    void sendNotificationToUser(Notification notification, NotificationTypeEnum type);
    void sendSprintWeatherNotificationToUser(String userToNotifyId, RetroSprintWeather retroSprintWeather);
    void sendRetroSprintNotificationToUser(String userToNotifyId, RetroSprint retroSprint);

    void sendRetroSprintCommentGroupUpdatedNotificationToUser(String userToNotifyId, RetroSprintCommentGroup retroSprintCommentGroup);
    void sendRetroSprintCommentGroupAddedNotificationToUser(String userToNotifyId, RetroSprintCommentGroup retroSprintCommentGroup);
    void sendRetroSprintCommentGroupDeletedNotificationToUser(String userToNotifyId, RetroSprintCommentGroup retroSprintCommentGroup);
    void sendRetroSprintCommentGroupVoteStartedUpdatedNotificationToUser(String userToNotifyId, Set<RetroSprintCommentGroup> retroSprintCommentGroups);
    void sendRetroSprintCommentGroupVoteAddedNotificationToUser(String userToNotifyId, RetroSprintCommentGroupVote retroSprintCommentGroupVote);
    void sendRetroSprintCommentGroupVoteDeletedNotificationToUser(String userToNotifyId, RetroSprintCommentGroupVote retroSprintCommentGroupVote);

    void sendRetroSprintCommentUpdatedNotificationToUser(String userToNotifyId, RetroSprintComment retroSprintUpdatedComment);
    void sendRetroSprintCommentAddedNotificationToUser(String userToNotifyId, RetroSprintComment retroSprintComment);
    void sendRetroSprintCommentDeletedNotificationToUser(String userToNotifyId, Set<RetroSprintComment> retroSprintUpdatedComments);
    void sendRetroSprintCommentDragDroppedInSameGroupNotificationToUser(String userToNotifyId, DragDropRetroSprintComment dragDropRetroSprintComment);
    void sendRetroSprintCommentDragDroppedInOtherGroupNotificationToUser(String userToNotifyId, DragDropRetroSprintComment dragDropRetroSprintComment);

    void sendRetroSprintCommentVoteAddedNotificationToUser(String userToNotifyId, RetroSprintCommentVote retroSprintCommentVote);
    void sendRetroSprintCommentVoteDeletedNotificationToUser(String userToNotifyId, RetroSprintCommentVote retroSprintCommentVote);

}
