package com.zak.domain.spi;

import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.RetroSprintCommentGroupVote;
import com.zak.domain.model.User;

import java.util.Optional;

public interface RetroSprintCommentGroupVotePersistencePort {

    Optional<RetroSprintCommentGroupVote> createRetroSprintCommentGroupVote(RetroSprintCommentGroupVote vote);
    Optional<RetroSprintCommentGroupVote> findByRetroSprintCommentGroupAndUser(RetroSprintCommentGroup retroSprintCommentGroup, User user);
    Optional<RetroSprintCommentGroupVote> updateRetroSprintCommentGroupVote(RetroSprintCommentGroupVote vote);
    boolean deleteRetroSprintCommentGroupVote(String voteId);
}
