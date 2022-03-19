package com.zak.domain.spi;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.*;
import java.util.Optional;
import java.util.Set;

public interface TaskPersistencePort {

    Optional<Task> createTask(Task task);
    Optional<Task> updateTask(Task task);
    Optional<Task> findTaskByTaskId(String taskId);
    void deleteTask(Task task);
    Set<Task> findTasksByUser(User user);
    Set<Task> findPersonalToDoTasksByUser(User user, EventStatus status);
    Set<Task> findToDoTasksByTeam(Team team, EventStatus status);
    Set<Task> findTasksByTeam(Team team);
    Set<Task> findTasksByTeamAndStatusAndPositions(Team team, EventStatus status, int fromPosition, int toPosition);
    Set<Task> findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositions(User user, EventStatus status, int fromPosition, int toPosition);
    Set<Task> findTasksByTeamAndStatusAndPositionSupp(Team team, EventStatus status, int position);
    Set<Task> findTasksByTeamAndStatusAndPositionSuppOrEqual(Team team, EventStatus status, int position);
    Set<Task> findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSupp(User user, EventStatus status, int position);
    Set<Task> findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSuppOrEqual(User user, EventStatus status, int position);
    Set<Task> findTasksByUserAndTeam(User user, Team team);
    Set<Task> findTasksByUserAndAccount(User user, Account account);
}
