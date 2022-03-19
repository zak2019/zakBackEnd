package com.zak.domain.model;

public class EventType {

    private long id;
    private String eventTypeId;
    private String code;
    private String name;
    private String bestPractices;

    public EventType() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBestPractices() {
        return bestPractices;
    }

    public void setBestPractices(String bestPractices) {
        this.bestPractices = bestPractices;
    }
}
