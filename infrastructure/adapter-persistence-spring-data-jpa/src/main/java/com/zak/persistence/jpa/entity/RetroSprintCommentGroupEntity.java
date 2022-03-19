package com.zak.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "retro_sprint_comment_group")
public class RetroSprintCommentGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String retroSprintCommentGroupId;
    private String name;
    @ManyToOne
    private RetroSprintEntity retroSprint;
    @JsonManagedReference
    @OneToMany(mappedBy="retroSprintCommentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RetroSprintCommentEntity> retroSprintCommentSet;
    @JsonManagedReference
    @OneToMany(mappedBy="retroSprintCommentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RetroSprintCommentGroupVoteEntity> retroSprintCommentGroupVoteSet;
    private Date creationDate;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
    private boolean voteStarted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRetroSprintCommentGroupId() {
        return retroSprintCommentGroupId;
    }

    public void setRetroSprintCommentGroupId(String retroSprintCommentGroupId) {
        this.retroSprintCommentGroupId = retroSprintCommentGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRetroSprint(RetroSprintEntity retroSprint) {
        this.retroSprint = retroSprint;
    }

    public void setRetroSprintCommentSet(Set<RetroSprintCommentEntity> retroSprintCommentSet) {
        this.retroSprintCommentSet = retroSprintCommentSet;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<RetroSprintCommentEntity> getRetroSprintCommentSet() {
        return retroSprintCommentSet;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isYellow() {
        return yellow;
    }

    public void setYellow(boolean yellow) {
        this.yellow = yellow;
    }

    public boolean isVoteStarted() {
        return voteStarted;
    }

    public void setVoteStarted(boolean voteStarted) {
        this.voteStarted = voteStarted;
    }

    public Set<RetroSprintCommentGroupVoteEntity> getRetroSprintCommentGroupVoteSet() {
        return retroSprintCommentGroupVoteSet;
    }

    public void setRetroSprintCommentGroupVoteSet(Set<RetroSprintCommentGroupVoteEntity> retroSprintCommentGroupVoteSet) {
        this.retroSprintCommentGroupVoteSet = retroSprintCommentGroupVoteSet;
    }
}
