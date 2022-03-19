package com.zak.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "user_activity")
public class UserActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userActivityId;
    @ManyToOne
    private UserEntity user;
    @OneToOne
    private EventCommentEntity eventComment;
    @ManyToOne
    private EventEntity event;
    private Date creationDate;


}
