package com.zak.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retro_sprint_weather")
public class RetroSprintWeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String retroSprintWeatherId;
    @OneToOne
    private UserEntity user;
    @ManyToOne
    private RetroSprintEntity retroSprint;
    private boolean sunnyClear;
    private boolean sunnyCloud;
    private boolean rainy;
    private boolean storm;
    private Date creationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRetroSprintWeatherId() {
        return retroSprintWeatherId;
    }

    public void setRetroSprintWeatherId(String retroSprintWeatherId) {
        this.retroSprintWeatherId = retroSprintWeatherId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setRetroSprint(RetroSprintEntity retroSprint) {
        this.retroSprint = retroSprint;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
