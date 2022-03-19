package com.zak.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zak.domain.enums.RetroSprintCommentType;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "retro_sprint_comment")
@Audited
public class RetroSprintCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String retroSprintCommentId;
    @Column(length = 3000)
    private String comment;
    @NotAudited
    @OneToOne
    private UserEntity user;
    @NotAudited
    @JsonBackReference
    @ManyToOne
    private RetroSprintCommentGroupEntity retroSprintCommentGroup;
    private Date creationDate;
    private int commentPosition;
    private RetroSprintCommentType commentType;
    @JsonManagedReference
    @OneToMany(mappedBy="retroSprintComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RetroSprintCommentVoteEntity> retroSprintCommentVoteSet;

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

    public void setRetroSprintCommentGroup(RetroSprintCommentGroupEntity retroSprintCommentGroup) {
        this.retroSprintCommentGroup = retroSprintCommentGroup;
    }

    public Set<RetroSprintCommentVoteEntity> getRetroSprintCommentVoteSet() {
        return retroSprintCommentVoteSet;
    }

    public void setRetroSprintCommentVoteSet(Set<RetroSprintCommentVoteEntity> retroSprintCommentVoteSet) {
        this.retroSprintCommentVoteSet = retroSprintCommentVoteSet;
    }
}
