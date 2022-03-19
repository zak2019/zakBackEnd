package com.zak.domain.model;

import com.zak.domain.enums.RetroSprintCommentType;

import java.util.Date;
import java.util.Set;


public class RetroSprintComment {

    private long id;
    private String retroSprintCommentId;
    private String comment;
    private User user;
    private RetroSprintCommentGroup retroSprintCommentGroup;
    private Date creationDate;
    private int commentPosition;
    private RetroSprintCommentType commentType;
    private Set<RetroSprintCommentVote> retroSprintCommentVoteSet;

    public RetroSprintComment() {
    }
    public RetroSprintComment(Long id) {
        this.id = id;
    }

    public RetroSprintComment(String retroSprintCommentId) {
        this.retroSprintCommentId = retroSprintCommentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRetroSprintCommentId() {
        return retroSprintCommentId;
    }

    public void setRetroSprintCommentId(String retroSprintCommentId) {
        this.retroSprintCommentId = retroSprintCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public int getCommentPosition() {
        return commentPosition;
    }

    public void setCommentPosition(int commentPosition) {
        this.commentPosition = commentPosition;
    }

    public RetroSprintCommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(RetroSprintCommentType commentType) {
        this.commentType = commentType;
    }

    public Set<RetroSprintCommentVote> getRetroSprintCommentVoteSet() {
        return retroSprintCommentVoteSet;
    }

    public void setRetroSprintCommentVoteSet(Set<RetroSprintCommentVote> retroSprintCommentVoteSet) {
        this.retroSprintCommentVoteSet = retroSprintCommentVoteSet;
    }
}
