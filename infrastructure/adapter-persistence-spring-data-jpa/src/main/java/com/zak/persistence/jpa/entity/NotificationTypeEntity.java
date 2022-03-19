package com.zak.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "notification_type")
public class NotificationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String notificationTypeId;
    private String code;
    private String name;
}
