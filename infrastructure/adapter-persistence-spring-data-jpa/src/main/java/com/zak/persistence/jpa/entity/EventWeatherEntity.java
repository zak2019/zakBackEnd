package com.zak.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "event_weather")
public class EventWeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String eventWeatherId;
    @JsonBackReference
    @ManyToOne
    private EventEntity event;
    @OneToOne
    private UserEntity user;
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

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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
