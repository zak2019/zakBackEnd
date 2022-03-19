package com.zak.application.service.api;

import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintCommentGroup;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintCommentGroupService {
    Optional<RetroSprintCommentGroup> createRetroSprintCommentGroupWithRetroSprintId(String retroSprintId, RetroSprintCommentGroup retroSprintCommentGroup);
    Optional<RetroSprintCommentGroup> createRetroSprintCommentGroupWithRetroSprint(RetroSprint retroSprint, RetroSprintCommentGroup retroSprintCommentGroup);
    Optional<RetroSprintCommentGroup> updateRetroSprintCommentGroup(String retroSprintId, RetroSprintCommentGroup retroSprintCommentGroup);
    Set<RetroSprintCommentGroup> startVoteRetroSprintCommentGroup(RetroSprint retroSprint);
    Set<RetroSprintCommentGroup> cancelVoteRetroSprintCommentGroup(RetroSprint retroSprint);
    boolean deleteRetroSprintCommentGroupByRetroSprintCommentGroupId(String retroSprintId, String retroSprintCommentGroupId);
}
