package com.zak.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "event_types")
public class EventTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String eventTypeId;
    private String code;
    private String name;
    @Lob @Basic(fetch = FetchType.LAZY)
    private String bestPractices;
}
