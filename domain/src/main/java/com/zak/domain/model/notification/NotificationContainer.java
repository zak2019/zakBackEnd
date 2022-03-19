package com.zak.domain.model.notification;

import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.model.*;

import java.util.Set;

public class NotificationContainer {

    private NotificationTypeEnum notificationType;

    private Notification headerNotification;
    private RetroSprint retroSprint;
    private RetroSprintWeather retroSprintWeather;
    private RetroSprintCommentGroup retroSprintCommentGroup;
    private Set<RetroSprintCommentGroup> retroSprintCommentGroups;
    private RetroSprintCommentGroupVote retroSprintCommentGroupVote;

    private RetroSprintComment retroSprintComment;
    private Set<RetroSprintComment> retroSprintUpdatedComments;
    private DragDropRetroSprintComment dragDropRetroSprintComment;

    private RetroSprintCommentVote retroSprintCommentVote;

    public NotificationTypeEnum getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationTypeEnum notificationType) {
        this.notificationType = notificationType;
    }

    public Notification getHeaderNotification() {
        return headerNotification;
    }

    public void setHeaderNotification(Notification headerNotification) {
        this.headerNotification = headerNotification;
    }

    public RetroSprint getRetroSprint() {
        return retroSprint;
    }

    public void setRetroSprint(RetroSprint retroSprint) {
        this.retroSprint = retroSprint;
    }

    public RetroSprintWeather getRetroSprintWeather() {
        return retroSprintWeather;
    }

    public void setRetroSprintWeather(RetroSprintWeather retroSprintWeather) {
        this.retroSprintWeather = retroSprintWeather;
    }

    public RetroSprintCommentGroup getRetroSprintCommentGroup() {
        return retroSprintCommentGroup;
    }

    public void setRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup) {
        this.retroSprintCommentGroup = retroSprintCommentGroup;
    }

    public Set<RetroSprintCommentGroup> getRetroSprintCommentGroups() {
        return retroSprintCommentGroups;
    }

    public void setRetroSprintCommentGroups(Set<RetroSprintCommentGroup> retroSprintCommentGroups) {
        this.retroSprintCommentGroups = retroSprintCommentGroups;
    }

    public RetroSprintCommentGroupVote getRetroSprintCommentGroupVote() {
        return retroSprintCommentGroupVote;
    }

    public void setRetroSprintCommentGroupVote(RetroSprintCommentGroupVote retroSprintCommentGroupVote) {
        this.retroSprintCommentGroupVote = retroSprintCommentGroupVote;
    }

    public RetroSprintComment getRetroSprintComment() {
        return retroSprintComment;
    }

    public void setRetroSprintComment(RetroSprintComment retroSprintComment) {
        this.retroSprintComment = retroSprintComment;
    }

    public Set<RetroSprintComment> getRetroSprintUpdatedComments() {
        return retroSprintUpdatedComments;
    }

    public void setRetroSprintUpdatedComments(Set<RetroSprintComment> retroSprintUpdatedComments) {
        this.retroSprintUpdatedComments = retroSprintUpdatedComments;
    }

    public RetroSprintCommentVote getRetroSprintCommentVote() {
        return retroSprintCommentVote;
    }

    public void setRetroSprintCommentVote(RetroSprintCommentVote retroSprintCommentVote) {
        this.retroSprintCommentVote = retroSprintCommentVote;
    }

    public DragDropRetroSprintComment getDragDropRetroSprintComment() {
        return dragDropRetroSprintComment;
    }

    public void setDragDropRetroSprintComment(DragDropRetroSprintComment dragDropRetroSprintComment) {
        this.dragDropRetroSprintComment = dragDropRetroSprintComment;
    }
}
