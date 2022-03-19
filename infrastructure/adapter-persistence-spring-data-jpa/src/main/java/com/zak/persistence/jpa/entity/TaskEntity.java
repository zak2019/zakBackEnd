package com.zak.persistence.jpa.entity;

import com.zak.domain.enums.EventStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String taskId;
    private String title;
    private String text;
    @ManyToOne
    private UserEntity createdBy;
    @ManyToOne
    private UserEntity affectedTo;
    @ManyToOne
    private TeamEntity team;
    @ManyToOne
    private AccountEntity account;
    private Date creationDate;
    private EventStatus status;
    private int position;

}
