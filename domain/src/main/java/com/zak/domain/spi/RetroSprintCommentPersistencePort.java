package com.zak.domain.spi;

import com.zak.domain.enums.RetroSprintCommentType;
import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintComment;
import com.zak.domain.model.RetroSprintCommentGroup;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintCommentPersistencePort {

    Optional<RetroSprintComment> createRetroSprintComment(RetroSprintComment retroSprintComment);
    Optional<RetroSprintComment> updateRetroSprintComment(RetroSprintComment retroSprintComment);
    boolean deleteRetroSprintComment(RetroSprintComment retroSprintComment);
    Optional<RetroSprintComment> findByRetroSprintCommentId(String retroSprintCommentId);
    Set<RetroSprintComment> findByRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup);
    Set<RetroSprintComment> findBySprintRetroCommentGroupAndByCommentTypeAndByPositions(
            RetroSprintCommentGroup retroSprintCommentGroup,
            RetroSprintCommentType type,
            int firstPosition,
            int secondPosition);
    Set<RetroSprintComment> findBySprintRetroGroupAndByCommentTypeStartingFromPosition(
            RetroSprintCommentGroup retroSprintCommentGroup,
            RetroSprintCommentType type,
            int position);
}
