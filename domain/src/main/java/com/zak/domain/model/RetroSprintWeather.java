package com.zak.domain.model;

import java.util.Date;

public class RetroSprintWeather {


    private long id;
    private String retroSprintWeatherId;
    private User user;
    private RetroSprint retroSprint;
    private boolean sunnyClear;
    private boolean sunnyCloud;
    private boolean rainy;
    private boolean storm;
    private Date creationDate;

    public RetroSprintWeather () {}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RetroSprint getRetroSprint() {
        return retroSprint;
    }

    public void setRetroSprint(RetroSprint retroSprint) {
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
