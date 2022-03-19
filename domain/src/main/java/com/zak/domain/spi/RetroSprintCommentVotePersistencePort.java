package com.zak.domain.spi;

import com.zak.domain.model.RetroSprintCommentVote;

import java.util.Optional;

public interface RetroSprintCommentVotePersistencePort {

    Optional<RetroSprintCommentVote> createRetroSprintCommentVote(RetroSprintCommentVote vote);
    Optional<RetroSprintCommentVote> findVoteByRetroSprintCommentVoteId(String voteId);
    boolean deleteVoteByRetroSprintCommentVoteId(String voteId);
}
