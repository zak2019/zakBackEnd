package com.zak.domain.spi;

import com.zak.domain.model.RetroSprintCommentGroup;
import java.util.Optional;

public interface RetroSprintCommentGroupPersistencePort {

    Optional<RetroSprintCommentGroup> createRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup);
    Optional<RetroSprintCommentGroup> updateRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup);
    boolean deleteRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup);
    Optional<RetroSprintCommentGroup> findByRetroSprintCommentGroupId(String retroSprintCommentGroupId);
}
