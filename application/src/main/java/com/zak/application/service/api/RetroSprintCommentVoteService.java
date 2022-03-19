package com.zak.application.service.api;

import com.zak.domain.model.RetroSprintCommentVote;

import java.util.Optional;

public interface RetroSprintCommentVoteService {
    Optional<RetroSprintCommentVote> createRetroSprintCommentVote(String userId, String commentId, String groupId, String retroSprintId);
    Optional<RetroSprintCommentVote> deleteRetroSprintCommentVote(String retroSprintCommentVoteId, String retroSprintCommentId, String retroSprintCommentGroupId, String retroSprintId);
}
