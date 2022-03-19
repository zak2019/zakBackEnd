package com.zak.domain.model;

import java.util.Date;
import java.util.Set;

public class Event {

    private long id;
    private String eventId;
    private String name;
    private EventType eventType;
    private Set<User> users;
    private User createdBy;
    private Set<EventWeather> eventWeatherSet;
    private Set<EventComment> eventCommentSet;
    private Team team;
    private Account account;
    private Date creationDate;
    private Date eventDate;
    private Date eventEndDate;
    private Date startedAt;

    public Event() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<EventWeather> getEventWeatherSet() {
        return eventWeatherSet;
    }

    public void setEventWeatherSet(Set<EventWeather> eventWeatherSet) {
        this.eventWeatherSet = eventWeatherSet;
    }

    public Set<EventComment> getEventCommentSet() {
        return eventCommentSet;
    }

    public void setEventCommentSet(Set<EventComment> eventCommentSet) {
        this.eventCommentSet = eventCommentSet;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
}
