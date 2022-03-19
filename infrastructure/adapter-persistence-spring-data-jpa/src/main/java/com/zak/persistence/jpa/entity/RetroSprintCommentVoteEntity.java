package com.zak.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zak.domain.model.RetroSprintComment;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retro_sprint_comment_vote")
@Audited
public class RetroSprintCommentVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String retroSprintCommentVoteId;
    @JsonBackReference
    @ManyToOne
    private RetroSprintCommentEntity retroSprintComment;
    @NotAudited
    @OneToOne
    private UserEntity user;
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

    public void setRetroSprintComment(RetroSprintCommentEntity retroSprintComment) {
        this.retroSprintComment = retroSprintComment;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
