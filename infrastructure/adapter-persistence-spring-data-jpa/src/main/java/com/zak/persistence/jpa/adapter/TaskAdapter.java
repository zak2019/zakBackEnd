package com.zak.persistence.jpa.adapter;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.*;
import com.zak.domain.spi.TaskPersistencePort;
import com.zak.persistence.jpa.entity.*;
import com.zak.persistence.jpa.repository.TaskRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.Set;

public class TaskAdapter implements TaskPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskAdapter(MapperFacade orikaMapperFacade,
                       TaskRepository taskRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> createTask(Task task) {
        TaskEntity taskEntity = orikaMapperFacade.map(task, TaskEntity.class);
        return Optional.of(orikaMapperFacade.map(taskRepository.save(taskEntity), Task.class));
    }

    @Override
    public Optional<Task> updateTask(Task task) {
        TaskEntity taskEntity = orikaMapperFacade.map(task, TaskEntity.class);
        return Optional.of(orikaMapperFacade.map(taskRepository.save(taskEntity), Task.class));
    }

    @Override
    public Optional<Task> findTaskByTaskId(String taskId) {
        return Optional.of(orikaMapperFacade.map(taskRepository.findTaskEntityByTaskId(taskId).get(), Task.class));
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(orikaMapperFacade.map(task, TaskEntity.class));
    }

    @Override
    public Set<Task> findTasksByUser(User user) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndAccountIsNullAndTeamIsNull(orikaMapperFacade.map(user, UserEntity.class))
                , Task.class);
    }

    @Override
    public Set<Task> findPersonalToDoTasksByUser(User user, EventStatus status) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndStatusAndAccountIsNullAndTeamIsNull(
                        orikaMapperFacade.map(user, UserEntity.class),
                        status)
                , Task.class);
    }

    @Override
    public Set<Task> findToDoTasksByTeam(Team team, EventStatus status) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByTeamAndStatus(
                        orikaMapperFacade.map(team, TeamEntity.class),
                        status)
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByTeam(Team team) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByTeam(
                        orikaMapperFacade.map(team, TeamEntity.class)
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByTeamAndStatusAndPositions(Team team, EventStatus status, int fromPosition, int toPosition) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByTeamAndStatusAndPositionBetween(
                        orikaMapperFacade.map(team, TeamEntity.class),
                        status,
                        fromPosition,
                        toPosition
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositions(User user, EventStatus status, int fromPosition, int toPosition) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndTeamIsNullAndStatusAndPositionBetween(
                        orikaMapperFacade.map(user, UserEntity.class),
                        status,
                        fromPosition,
                        toPosition
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByTeamAndStatusAndPositionSupp(Team team, EventStatus status, int position) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByTeamAndStatusAndPositionGreaterThan(
                        orikaMapperFacade.map(team, TeamEntity.class),
                        status,
                        position
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByTeamAndStatusAndPositionSuppOrEqual(Team team, EventStatus status, int position) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByTeamAndStatusAndPositionGreaterThanEqual(
                        orikaMapperFacade.map(team, TeamEntity.class),
                        status,
                        position
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSupp(User user, EventStatus status, int position) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndTeamIsNullAndStatusAndPositionGreaterThan(
                        orikaMapperFacade.map(user, UserEntity.class),
                        status,
                        position
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSuppOrEqual(User user, EventStatus status, int position) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndTeamIsNullAndStatusAndPositionGreaterThanEqual(
                        orikaMapperFacade.map(user, UserEntity.class),
                        status,
                        position
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByUserAndTeam(User user, Team team) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndTeam(
                        orikaMapperFacade.map(user, UserEntity.class),
                        orikaMapperFacade.map(team, TeamEntity.class)
                )
                , Task.class);
    }

    @Override
    public Set<Task> findTasksByUserAndAccount(User user, Account account) {
        return orikaMapperFacade.mapAsSet(
                taskRepository.findTaskEntityByAffectedToAndAccount(
                        orikaMapperFacade.map(user, UserEntity.class),
                        orikaMapperFacade.map(account, AccountEntity.class)
                )
                , Task.class);
    }
}
