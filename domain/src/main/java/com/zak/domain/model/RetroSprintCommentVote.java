package com.zak.domain.model;

import java.util.Date;

public class RetroSprintCommentVote {

    private long id;
    private String retroSprintCommentVoteId;
    private RetroSprintComment retroSprintComment;
    private User user;
    private Date creationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRetroSprintCommentVoteId() {
        return retroSprintCommentVoteId;
    }

    public void setRetroSprintCommentVoteId(String retroSprintCommentVoteId) {
        this.retroSprintCommentVoteId = retroSprintCommentVoteId;
    }

    public RetroSprintComment getRetroSprintComment() {
        return retroSprintComment;
    }

    public void setRetroSprintComment(RetroSprintComment retroSprintComment) {
        this.retroSprintComment = retroSprintComment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
