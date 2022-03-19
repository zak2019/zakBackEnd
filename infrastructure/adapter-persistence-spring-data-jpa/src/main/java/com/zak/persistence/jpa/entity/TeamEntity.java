package com.zak.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String teamId;
    private String teamName;
    private Date creationDate;
//    @OneToOne
//    private UserEntity creator;
    @OneToOne
    private AccountEntity account;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_user_assoc",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> linkedUsers = new HashSet<UserEntity>();
}
