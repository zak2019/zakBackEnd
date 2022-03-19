package com.zak.domain.model;

import java.util.Date;
import java.util.Set;

public class RetroSprintCommentGroup {


    private long id;
    private String retroSprintCommentGroupId;
    private String name;
    private RetroSprint retroSprint;
    private Set<RetroSprintComment> retroSprintCommentSet;
    private Set<RetroSprintCommentGroupVote> retroSprintCommentGroupVoteSet;
    private Date creationDate;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
    private boolean voteStarted;

    public RetroSprintCommentGroup() {
    }
    public RetroSprintCommentGroup(long id) {
        this.id = id;
    }

    public RetroSprintCommentGroup(String retroSprintCommentGroupId) {
        this.retroSprintCommentGroupId = retroSprintCommentGroupId;
    }

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

    public RetroSprint getRetroSprint() {
        return retroSprint;
    }

    public void setRetroSprint(RetroSprint retroSprint) {
        this.retroSprint = retroSprint;
    }

    public Set<RetroSprintComment> getRetroSprintCommentSet() {
        return retroSprintCommentSet;
    }

    public void setRetroSprintCommentSet(Set<RetroSprintComment> retroSprintCommentSet) {
        this.retroSprintCommentSet = retroSprintCommentSet;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Set<RetroSprintCommentGroupVote> getRetroSprintCommentGroupVoteSet() {
        return retroSprintCommentGroupVoteSet;
    }

    public void setRetroSprintCommentGroupVoteSet(Set<RetroSprintCommentGroupVote> retroSprintCommentGroupVoteSet) {
        this.retroSprintCommentGroupVoteSet = retroSprintCommentGroupVoteSet;
    }
}
