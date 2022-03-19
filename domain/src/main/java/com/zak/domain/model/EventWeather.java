package com.zak.domain.model;

import java.util.Date;

public class EventWeather {

    private long id;
    private String eventWeatherId;
    private Event event;
    private User user;
    private Date creationDate;
    private boolean sunnyClear;
    private boolean sunnyCloud;
    private boolean rainy;
    private boolean storm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventWeatherId() {
        return eventWeatherId;
    }

    public void setEventWeatherId(String eventWeatherId) {
        this.eventWeatherId = eventWeatherId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isSunnyClear() {
        return sunnyClear;
    }

    public void setSunnyClear(boolean sunnyClear) {
        this.sunnyClear = sunnyClear;
    }

    public boolean isSunnyCloud() {
        return sunnyCloud;
    }

    public void setSunnyCloud(boolean sunnyCloud) {
        this.sunnyCloud = sunnyCloud;
    }

    public boolean isRainy() {
        return rainy;
    }

    public void setRainy(boolean rainy) {
        this.rainy = rainy;
    }

    public boolean isStorm() {
        return storm;
    }

    public void setStorm(boolean storm) {
        this.storm = storm;
    }
}
