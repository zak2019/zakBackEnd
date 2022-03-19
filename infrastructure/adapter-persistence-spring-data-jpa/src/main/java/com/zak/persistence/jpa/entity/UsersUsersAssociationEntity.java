package com.zak.persistence.jpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users_users_associations")
public class UsersUsersAssociationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String associationId;

    @ManyToOne
    @JoinColumn(name="invited_user")
    private UserEntity invitedUser;

    @ManyToOne
    @JoinColumn(name="inviter")
    private UserEntity inviter;

    @ManyToOne
    @JoinColumn(name="account")
    private AccountEntity account;

    private Date creationDate;

    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "associations_user_roles",
            joinColumns = @JoinColumn(name = "association_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();

    public UsersUsersAssociationEntity(String associationId, UserEntity invitedUser,
                                       Date creationDate, boolean isEnabled, Set<UserRoleEntity> roles) {
        this.associationId = associationId;
        this.invitedUser = invitedUser;
        this.creationDate = creationDate;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public UsersUsersAssociationEntity(AccountEntity account,Set<UserRoleEntity> roles) {
        this.account = account;
        this.roles = roles;
    }

    public UsersUsersAssociationEntity(String associationId, UserEntity invitedUser,
                                       Date creationDate, boolean isEnabled) {
        this.associationId = associationId;
        this.invitedUser = invitedUser;
        this.creationDate = creationDate;
        this.isEnabled = isEnabled;
    }
}
