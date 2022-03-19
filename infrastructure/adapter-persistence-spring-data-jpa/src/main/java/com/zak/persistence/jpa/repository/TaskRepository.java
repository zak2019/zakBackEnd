package com.zak.persistence.jpa.repository;

import com.zak.domain.enums.EventStatus;
import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.entity.TaskEntity;
import com.zak.persistence.jpa.entity.TeamEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findTaskEntityByTaskId(String taskId);
    Set<TaskEntity> findTaskEntityByAffectedToAndAccountIsNullAndTeamIsNull(UserEntity user);
    Set<TaskEntity> findTaskEntityByAffectedToAndStatusAndAccountIsNullAndTeamIsNull(UserEntity user, EventStatus status);
    Set<TaskEntity> findTaskEntityByTeamAndStatus(TeamEntity team, EventStatus status);
    Set<TaskEntity> findTaskEntityByTeam(TeamEntity team);
    Set<TaskEntity> findTaskEntityByTeamAndStatusAndPositionBetween(TeamEntity team, EventStatus status, int fromPosition, int toPosition);
    Set<TaskEntity> findTaskEntityByAffectedToAndTeamIsNullAndStatusAndPositionBetween(UserEntity user, EventStatus status, int fromPosition, int toPosition);
    Set<TaskEntity> findTaskEntityByTeamAndStatusAndPositionGreaterThan(TeamEntity team, EventStatus status, int position);
    Set<TaskEntity> findTaskEntityByTeamAndStatusAndPositionGreaterThanEqual(TeamEntity team, EventStatus status, int position);
    Set<TaskEntity> findTaskEntityByAffectedToAndTeamIsNullAndStatusAndPositionGreaterThan(UserEntity user, EventStatus status, int position);
    Set<TaskEntity> findTaskEntityByAffectedToAndTeamIsNullAndStatusAndPositionGreaterThanEqual(UserEntity user, EventStatus status, int position);
    Set<TaskEntity> findTaskEntityByAffectedToAndTeam(UserEntity user, TeamEntity team);
    Set<TaskEntity> findTaskEntityByAffectedToAndAccount(UserEntity user, AccountEntity account);

}
