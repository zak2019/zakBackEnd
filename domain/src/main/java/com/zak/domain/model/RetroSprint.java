package com.zak.domain.model;
import com.zak.domain.enums.EventStatus;

import java.util.Date;
import java.util.Set;

public class RetroSprint {


    private long id;
    private String retroSprintId;
    private String retroName;
    private Event eventRetro;
    private User createdBy;
    private Date creationDate;
    private Set<RetroSprintWeather> retroSprintWeatherSet;
    private Set<RetroSprintCommentGroup> retroSprintCommentGroupSet;
    private EventStatus weatherStatus;
    private EventStatus boardStatus;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
    private EventStatus status;

    public RetroSprint() {
    }


    public RetroSprint(String retroSprintId) {
        this.retroSprintId = retroSprintId;
    }

    public RetroSprint(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRetroSprintId() {
        return retroSprintId;
    }

    public void setRetroSprintId(String retroSprintId) {
        this.retroSprintId = retroSprintId;
    }

    public Event getEventRetro() {
        return eventRetro;
    }

    public String getRetroName() {
        return retroName;
    }

    public void setRetroName(String retroName) {
        this.retroName = retroName;
    }

    public void setEventRetro(Event eventRetro) {
        this.eventRetro = eventRetro;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<RetroSprintWeather> getRetroSprintWeatherSet() {
        return retroSprintWeatherSet;
    }

    public void setRetroSprintWeatherSet(Set<RetroSprintWeather> retroSprintWeatherSet) {
        this.retroSprintWeatherSet = retroSprintWeatherSet;
    }

    public Set<RetroSprintCommentGroup> getRetroSprintCommentGroupSet() {
        return retroSprintCommentGroupSet;
    }

    public void setRetroSprintCommentGroupSet(Set<RetroSprintCommentGroup> retroSprintCommentGroupSet) {
        this.retroSprintCommentGroupSet = retroSprintCommentGroupSet;
    }

    public void addRetroSprintCommentGroupSet(RetroSprintCommentGroup retroSprintCommentGroupSet) {
        this.retroSprintCommentGroupSet.add(retroSprintCommentGroupSet);
    }

    public EventStatus getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(EventStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public EventStatus getBoardStatus() {
        return boardStatus;
    }

    public void setBoardStatus(EventStatus boardStatus) {
        this.boardStatus = boardStatus;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isYellow() {
        return yellow;
    }

    public void setYellow(boolean yellow) {
        this.yellow = yellow;
    }
}
