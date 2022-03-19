package com.zak.persistence.jpa.repository;

import com.zak.domain.enums.RetroSprintCommentType;
import com.zak.persistence.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface RetroSprintCommentRepository extends JpaRepository<RetroSprintCommentEntity, Long> {

    Set<RetroSprintCommentEntity> findByRetroSprintCommentGroup(RetroSprintCommentGroupEntity retroSprintCommentGroupEntity);
//    Optional<RetroSprintCommentEntity> findByRetroSprintAndUser(RetroSprintEntity retroSprintEntity, UserEntity user);
    Optional<RetroSprintCommentEntity> findByRetroSprintCommentId(String retroSprintCommentId);

    @Query("select c" +
            " from RetroSprintCommentEntity c" +
            " where c.retroSprintCommentGroup = :groupEntity" +
            " and c.commentType = :type" +
            " and c.commentPosition >= :firstPosition" +
            " and c.commentPosition <= :secondPosition")
    Set<RetroSprintCommentEntity> findBySprintRetroCommentGroupAndByCommentTypeAndByPositions(
            @Param("groupEntity") RetroSprintCommentGroupEntity groupEntity,
            @Param("type") RetroSprintCommentType type,
            @Param("firstPosition") int firstPosition,
            @Param("secondPosition") int secondPosition);

    @Query("select c" +
            " from RetroSprintCommentEntity c" +
            " where c.retroSprintCommentGroup = :groupEntity" +
            " and c.commentType = :type" +
            " and c.commentPosition >= :position")
    Set<RetroSprintCommentEntity> findBySprintRetroGroupAndByCommentTypeStartingFromPosition(
            @Param("groupEntity") RetroSprintCommentGroupEntity groupEntity,
            @Param("type") RetroSprintCommentType type,
            @Param("position") int position);

    @Transactional
    @Modifying
    @Query(" delete" +
            " from RetroSprintCommentEntity c" +
            " where c.retroSprintCommentId = :commentId")
    void deleteRetroSprintCommentByRetroSprintCommentId(@Param("commentId") String commentId);
}
