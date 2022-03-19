package com.zak.application.service.api;

import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.RetroSprintCommentGroupVote;

import java.util.Optional;

public interface RetroSprintCommentGroupVoteService {
    Optional<RetroSprintCommentGroupVote> createRetroSprintCommentGroupVote(String retroSprintId, String userId, String retroSprintCommentGroupId);
    Optional<RetroSprintCommentGroupVote> updateRetroSprintCommentGroupVote(RetroSprintCommentGroupVote vote, RetroSprintCommentGroup group);
    Optional<RetroSprintCommentGroupVote> deleteRetroSprintCommentGroupVote(String retroSprintId, String userId, String retroSprintCommentGroupId);
}
