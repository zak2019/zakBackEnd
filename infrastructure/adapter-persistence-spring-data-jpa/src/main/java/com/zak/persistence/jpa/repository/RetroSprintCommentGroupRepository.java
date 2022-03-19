package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.RetroSprintCommentGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetroSprintCommentGroupRepository extends JpaRepository<RetroSprintCommentGroupEntity, Long> {

    Optional<RetroSprintCommentGroupEntity> findByRetroSprintCommentGroupId(String retroSprintCommentGroupId);
}
