package com.zak.domain.model;

import java.util.Date;

public class Notification {

    private long id;
    private String notificationId;
    private NotificationType notificationType;
    private User userToNotify;
    private Event event;
    private Team team;
    private Date creationDate;
    private boolean consulted;
    private boolean pageConsulted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public User getUserToNotify() {
        return userToNotify;
    }

    public void setUserToNotify(User userToNotify) {
        this.userToNotify = userToNotify;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isConsulted() {
        return consulted;
    }

    public void setConsulted(boolean consulted) {
        this.consulted = consulted;
    }

    public boolean isPageConsulted() {
        return pageConsulted;
    }

    public void setPageConsulted(boolean pageConsulted) {
        this.pageConsulted = pageConsulted;
    }
}
