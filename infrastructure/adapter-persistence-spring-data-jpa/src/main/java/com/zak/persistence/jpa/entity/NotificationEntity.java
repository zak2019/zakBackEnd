package com.zak.persistence.jpa.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String notificationId;
    @OneToOne
    private NotificationTypeEntity notificationType;
    @OneToOne
    private UserEntity userToNotify;
    @OneToOne
    private EventEntity event;
    @OneToOne
    private TeamEntity team;
    private Date creationDate;
    private boolean consulted;
    private boolean pageConsulted;
}
