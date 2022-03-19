package com.zak.domain.model;

import java.util.Date;

public class RetroSprintCommentGroupVote {

    private long id;
    private String retroSprintCommentGroupVoteId;
    private User user;
    private RetroSprintCommentGroup retroSprintCommentGroup;
    private Date creationDate;
    private int votesNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRetroSprintCommentGroupVoteId() {
        return retroSprintCommentGroupVoteId;
    }

    public void setRetroSprintCommentGroupVoteId(String retroSprintCommentGroupVoteId) {
        this.retroSprintCommentGroupVoteId = retroSprintCommentGroupVoteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RetroSprintCommentGroup getRetroSprintCommentGroup() {
        return retroSprintCommentGroup;
    }

    public void setRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup) {
        this.retroSprintCommentGroup = retroSprintCommentGroup;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getVotesNumber() {
        return votesNumber;
    }

    public void setVotesNumber(int votesNumber) {
        this.votesNumber = votesNumber;
    }
}
