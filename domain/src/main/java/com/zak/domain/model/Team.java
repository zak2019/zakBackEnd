package com.zak.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Team {

    private long id;
    private String teamId;
    private String teamName;
    private Date creationDate;
    private Account account;
    private Set<User> linkedUsers = new HashSet<User>();

    public Team() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public Set<User> getLinkedUsers() {
        return linkedUsers;
    }

    public void setLinkedUsers(Set<User> linkedUsers) {
        this.linkedUsers = linkedUsers;
    }
}
