package com.zak.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zak.domain.enums.EventStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "retro_sprint")
public class RetroSprintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String retroSprintId;
    private String retroName;
    @OneToOne
    private EventEntity eventRetro;
    @OneToOne
    private UserEntity createdBy;
    private Date creationDate;
    @JsonManagedReference
    @OneToMany(mappedBy="retroSprint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RetroSprintWeatherEntity> retroSprintWeatherSet;
    @JsonManagedReference
    @OneToMany(mappedBy="retroSprint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RetroSprintCommentGroupEntity> retroSprintCommentGroupSet;
    private EventStatus weatherStatus;
    private EventStatus boardStatus;
    private EventStatus status;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
}
