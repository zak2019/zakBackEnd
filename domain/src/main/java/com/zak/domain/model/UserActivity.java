package com.zak.domain.model;


import java.util.Date;

public class UserActivity {

    private String userActivityId;
    private User user;
    private EventComment eventComment;
    private Event event;
    private Date creationDate;

    public String getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(String userActivityId) {
        this.userActivityId = userActivityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventComment getEventComment() {
        return eventComment;
    }

    public void setEventComment(EventComment eventComment) {
        this.eventComment = eventComment;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
