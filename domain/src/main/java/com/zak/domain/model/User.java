package com.zak.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String userId;
    private String secretId;
    private String username;
    private String firstName;
    private String lastName;
    private String linkedInId;
    private String roleInProject;
    private String email;
    private String password;
    private Set<UserRole> roles = new HashSet<UserRole>();
    private boolean isEnabled;
    private Set<UsersUsersAssociation> inviter = new HashSet<UsersUsersAssociation>();
    private Set<UsersUsersAssociation> invitedUsers = new HashSet<UsersUsersAssociation>();
    private Date creationDate;

    public User() {
    }

    public User(Long id, String userId, String username, String email, String password,
                Set<UserRole> roles, boolean isEnabled) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isEnabled = isEnabled;
    }

    public User(String userId, String username, String email, String password, Set<UserRole> roles, boolean isEnabled) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isEnabled = isEnabled;
    }

    public User(String username, String email, String password, Date creationDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId) {
        this.linkedInId = linkedInId;
    }

    public String getRoleInProject() {
        return roleInProject;
    }

    public void setRoleInProject(String roleInProject) {
        this.roleInProject = roleInProject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<UsersUsersAssociation> getInviter() {
        return inviter;
    }

    public void setInviter(Set<UsersUsersAssociation> inviter) {
        this.inviter = inviter;
    }

    public Set<UsersUsersAssociation> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(Set<UsersUsersAssociation> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", isEnabled=" + isEnabled +
                ", creationDate=" + creationDate +
                '}';
    }
}
