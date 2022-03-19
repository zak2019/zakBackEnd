package com.zak.application.service.api;

import com.zak.domain.model.DragDropRetroSprintComment;
import com.zak.domain.model.RetroSprintComment;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintCommentService {
    Optional<RetroSprintComment> createRetroSprintComment(String retroSprintId, String userId, String retroSprintCommentGroupId, RetroSprintComment retroSprintComment);
    Optional<RetroSprintComment> updateRetroSprintComment(String retroSprintId, String retroSprintCommentGroupId, RetroSprintComment retroSprintComment);
    Set<RetroSprintComment> getCommentsByRetroSprintCommentGroupId(String retroSprintCommentGroupId);
    DragDropRetroSprintComment changeRetroSprintCommentPosition(String retroSprintCommentId, String retroSprintCommentGroupId, String retroSprintId, int toPosition);
    DragDropRetroSprintComment changeRetroSprintCommentPositionAndGroup(String retroSprintId, String retroSprintCommentId, String fromGroupId, String toGroupId, int toPosition);
    Set<RetroSprintComment> deleteRetroSprintCommentByRetroSprintCommentIdAndGroupId(String retroSprintCommentId, String retroSprintCommentGroupId, String retroSprintId);
}
