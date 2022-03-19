package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RetroSprintCommentGroupVoteRepository extends JpaRepository<RetroSprintCommentGroupVoteEntity, Long> {

    Optional<RetroSprintCommentGroupVoteEntity> findByRetroSprintCommentGroupAndUser(RetroSprintCommentGroupEntity retroSprintCommentGroup,
                                                                                     UserEntity user);
    @Transactional
    @Modifying
    @Query(" delete" +
            " from RetroSprintCommentGroupVoteEntity v" +
            " where v.retroSprintCommentGroupVoteId = :voteId")
    void deleteVoteByRetroSprintCommentGroupVoteId(@Param("voteId") String voteId);
}
