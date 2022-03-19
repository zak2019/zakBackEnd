package com.zak.domain.model;

import java.util.Set;


public class DragDropRetroSprintComment {

    private String dragDropCommentId;
    private String fromGroup;
    private String toGroup;
    private Set<RetroSprintComment> commentsUpdatedPosition;

    public DragDropRetroSprintComment(String dragDropCommentId, Set<RetroSprintComment> commentsUpdatedPosition) {
        this.dragDropCommentId = dragDropCommentId;
        this.commentsUpdatedPosition = commentsUpdatedPosition;
    }

    public DragDropRetroSprintComment(String dragDropCommentId, Set<RetroSprintComment> commentsUpdatedPosition, String fromGroup, String toGroup) {
        this.dragDropCommentId = dragDropCommentId;
        this.fromGroup = fromGroup;
        this.toGroup = toGroup;
        this.commentsUpdatedPosition = commentsUpdatedPosition;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getToGroup() {
        return toGroup;
    }

    public void setToGroup(String toGroup) {
        this.toGroup = toGroup;
    }

    public String getDragDropCommentId() {
        return dragDropCommentId;
    }

    public void setDragDropCommentId(String dragDropCommentId) {
        this.dragDropCommentId = dragDropCommentId;
    }

    public Set<RetroSprintComment> getCommentsUpdatedPosition() {
        return commentsUpdatedPosition;
    }

    public void setCommentsUpdatedPosition(Set<RetroSprintComment> commentsUpdatedPosition) {
        this.commentsUpdatedPosition = commentsUpdatedPosition;
    }
}
