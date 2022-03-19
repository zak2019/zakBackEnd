package com.zak.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String eventId;
    private String name;
    @OneToOne
    private EventTypeEntity eventType;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_user_assoc",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users;
    @OneToOne
    private UserEntity createdBy;
    @OneToOne
    private TeamEntity team;
    @OneToOne
    private AccountEntity account;
    private Date creationDate;
    private Date eventDate;
    private Date eventEndDate;
    private Date startedAt;
    @JsonManagedReference
    @OneToMany(mappedBy="event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EventWeatherEntity> eventWeatherSet;
    @JsonManagedReference
    @OneToMany(mappedBy="event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EventCommentEntity> eventCommentSet;
}
