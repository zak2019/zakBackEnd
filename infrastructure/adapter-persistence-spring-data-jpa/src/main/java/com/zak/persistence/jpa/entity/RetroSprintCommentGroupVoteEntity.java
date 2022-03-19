package com.zak.persistence.jpa.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retro_sprint_comment_group_vote")
public class RetroSprintCommentGroupVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String retroSprintCommentGroupVoteId;
    @OneToOne
    private UserEntity user;
    @ManyToOne
    private RetroSprintCommentGroupEntity retroSprintCommentGroup;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setRetroSprintCommentGroup(RetroSprintCommentGroupEntity retroSprintCommentGroup) {
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
