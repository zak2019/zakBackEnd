package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.RetroSprintCommentGroupEntity;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupVoteEntity;
import com.zak.persistence.jpa.entity.RetroSprintCommentVoteEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RetroSprintCommentVoteRepository extends JpaRepository<RetroSprintCommentVoteEntity, Long> {

    Optional<RetroSprintCommentVoteEntity> findByRetroSprintCommentVoteId(String retroSprintCommentVoteId);

    @Transactional
    @Modifying
    @Query(" delete" +
            " from RetroSprintCommentVoteEntity v" +
            " where v.retroSprintCommentVoteId = :voteId")
    void deleteVoteByRetroSprintCommentVoteId(@Param("voteId") String voteId);
}
