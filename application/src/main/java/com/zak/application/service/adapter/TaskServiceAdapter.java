package com.zak.application.service.adapter;

import com.zak.application.service.api.*;
import com.zak.domain.enums.EventStatus;
import com.zak.domain.exception.*;
import com.zak.domain.model.*;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.IntStream;

public class TaskServiceAdapter implements TaskService {

    private final TaskPersistencePort taskPersistencePort;
    private final UserService userService;
    private final TeamService teamService;
    private final AccountService accountService;
    private final IdGererator idGererator;


    @Autowired
    public TaskServiceAdapter(TaskPersistencePort taskPersistencePort,
                              UserService userService,
                              TeamService teamService,
                              AccountService accountService,
                              IdGererator idGererator) {
        this.taskPersistencePort = taskPersistencePort;
        this.userService = userService;
        this.teamService = teamService;
        this.accountService = accountService;
        this.idGererator = idGererator;
    }


    @Override
    public Optional<Task> createTask(String userId, Task task) {

        User user = getUser(userId);
        Set<Task> tasks = this.taskPersistencePort.findPersonalToDoTasksByUser(user, EventStatus.TO_DO);

        return setAndSaveTaskData(task, user, null, tasks);
    }

    @Override
    public Optional<Task> createTeamTask(String userId, String teamId, Task task) {

        User user = getUser(userId);
        Team team = getTeam(teamId);
        Set<Task> tasks = this.taskPersistencePort.findToDoTasksByTeam(team, EventStatus.TO_DO);

        return setAndSaveTaskData(task, user, team, tasks);
    }

    private Optional<Task> setAndSaveTaskData(Task task, User user, Team team, Set<Task> tasks) {
        task.setTaskId(idGererator.generateUniqueId());
        task.setCreationDate(new Date());
        task.setCreatedBy(user);
        if (team == null) {
            task.setAffectedTo(user);
        } else if (task.getAffectedTo() != null) {
            if (task.getAffectedTo().getUserId() != user.getUserId()) {
                task.setAffectedTo(getUser(task.getAffectedTo().getUserId()));
            } else {
                task.setAffectedTo(user);
            }
        } else {
            task.setAffectedTo(null);
        }
        task.setTeam(team);
        task.setStatus(EventStatus.TO_DO);
        task.setPosition(tasks.size() + 1);

        return taskPersistencePort.createTask(task);
    }

    @Override
    public Set<Task> updateTaskPositionAndTaskList(String taskId, EventStatus newStatus, int toPosition) {
        Task taskFromDB = getTaskByTaskId(taskId);
        Team team = taskFromDB.getTeam();
        User user = taskFromDB.getAffectedTo();
        Set<Task> updatedTask = new HashSet<>();
        if (team != null) {
            Set<Task> tasks = taskPersistencePort.findTasksByTeamAndStatusAndPositionSupp(
                    team,
                    taskFromDB.getStatus(),
                    taskFromDB.getPosition());
            tasks.forEach(task -> {
                task.setPosition(task.getPosition() - 1);
                updatedTask.add(taskPersistencePort.updateTask(task).get());
            });

            Set<Task> tasks2 = taskPersistencePort.findTasksByTeamAndStatusAndPositionSuppOrEqual(
                    team,
                    newStatus,
                    toPosition);
            tasks2.forEach(task -> {
                task.setPosition(task.getPosition() + 1);
                updatedTask.add(taskPersistencePort.updateTask(task).get());
            });
        } else if (user != null) {
            Set<Task> tasks = taskPersistencePort.findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSupp(
                    user,
                    taskFromDB.getStatus(),
                    taskFromDB.getPosition());
            tasks.forEach(task -> {
                task.setPosition(task.getPosition() - 1);
                updatedTask.add(taskPersistencePort.updateTask(task).get());
            });

            Set<Task> tasks2 = taskPersistencePort.findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSuppOrEqual(
                    user,
                    newStatus,
                    toPosition);
            tasks2.forEach(task -> {
                task.setPosition(task.getPosition() + 1);
                updatedTask.add(taskPersistencePort.updateTask(task).get());
            });
        }

        taskFromDB.setStatus(newStatus);
        taskFromDB.setPosition(toPosition);
        Task savedTask = taskPersistencePort.updateTask(taskFromDB).get();
        updatedTask.add(savedTask);
        return updatedTask;
    }


    @Override
    public Set<Task> updateTaskPosition(String taskId, int toPosition) {
        Task taskFromDB = getTaskByTaskId(taskId);
        int position = taskFromDB.getPosition();
        Team team = taskFromDB.getTeam();
        User user = taskFromDB.getAffectedTo();
        EventStatus status = taskFromDB.getStatus();
        Set<Task> updatedTask = new HashSet<>();
        if (team != null) {
            if (toPosition > position) {
                Set<Task> tasks = taskPersistencePort.findTasksByTeamAndStatusAndPositions(
                        team,
                        status,
                        position,
                        toPosition);
                tasks.forEach(task -> {
                    if (task.getTaskId() != taskFromDB.getTaskId()) {
                        task.setPosition(task.getPosition() - 1);
                        updatedTask.add(taskPersistencePort.updateTask(task).get());
                    }
                });
            } else if (toPosition < position) {
                Set<Task> tasks = taskPersistencePort.findTasksByTeamAndStatusAndPositions(
                        team,
                        status,
                        toPosition,
                        position);
                tasks.forEach(task -> {
                    if (task.getTaskId() != taskFromDB.getTaskId()) {
                        task.setPosition(task.getPosition() + 1);
                        updatedTask.add(taskPersistencePort.updateTask(task).get());
                    }
                });
            }

        } else if (user != null) {
            if (toPosition > position) {
                Set<Task> tasks = taskPersistencePort.findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositions(
                        user,
                        status,
                        position,
                        toPosition);
                tasks.forEach(task -> {
                    if (task.getTaskId() != taskFromDB.getTaskId()) {
                        task.setPosition(task.getPosition() - 1);
                        updatedTask.add(taskPersistencePort.updateTask(task).get());
                    }
                });
            } else if (toPosition < position) {
                Set<Task> tasks = taskPersistencePort.findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositions(
                        user,
                        status,
                        toPosition,
                        position);
                tasks.forEach(task -> {
                    if (task.getTaskId() != taskFromDB.getTaskId()) {
                        task.setPosition(task.getPosition() + 1);
                        updatedTask.add(taskPersistencePort.updateTask(task).get());
                    }
                });
            }
        }


        taskFromDB.setPosition(toPosition);
        Task savedTask = taskPersistencePort.updateTask(taskFromDB).get();
        updatedTask.add(savedTask);
        return updatedTask;
    }

    @Override
    public Optional<Task> updateTask(Task task) {

        if (task.getAffectedTo() != null) {
            task.setAffectedTo(getUser(task.getAffectedTo().getUserId()));
        }
        if (task.getCreatedBy() != null) {
            task.setCreatedBy(getUser(task.getCreatedBy().getUserId()));
        }
        if (task.getTeam() != null) {
            task.setTeam(getTeam(task.getTeam().getTeamId()));
        }
        Task taskFromDB = getTaskByTaskId(task.getTaskId());

        task.setId(taskFromDB.getId());

        return taskPersistencePort.updateTask(task);
    }

    private User getUser(String userId) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Override
    public Task getTaskByTaskId(String taskId) {
        Optional<Task> task = taskPersistencePort.findTaskByTaskId(taskId);
        if (!task.isPresent()) {
            throw new TaskException();
        }
        return task.get();
    }

    @Override
    public void deleteTaskByTaskId(String taskId) {
        Task taskFromDB = getTaskByTaskId(taskId);
        if (taskFromDB.getTeam() != null) {
            Set<Task> tasks = taskPersistencePort.findTasksByTeamAndStatusAndPositionSupp(
                    taskFromDB.getTeam(),
                    taskFromDB.getStatus(),
                    taskFromDB.getPosition());
            tasks.forEach(task -> {
                task.setPosition(task.getPosition() - 1);
                taskPersistencePort.updateTask(task);
            });
        } else if (taskFromDB.getAffectedTo() != null) {
            Set<Task> tasks = taskPersistencePort.findTasksByAffectedToUserAndTeamIsNullAndStatusAndPositionSupp(
                    taskFromDB.getAffectedTo(),
                    taskFromDB.getStatus(),
                    taskFromDB.getPosition());
            tasks.forEach(task -> {
                task.setPosition(task.getPosition() - 1);
                taskPersistencePort.updateTask(task);
            });
        }
        taskPersistencePort.deleteTask(taskFromDB);
    }

    @Override
    public Set<Task> getTasksByUserId(String userId) {
        User user = getUser(userId);

        Set<Task> tasks = taskPersistencePort.findTasksByUser(user);
        return tasks;
    }

    @Override
    public Set<Task> getTasksTeamId(String teamId) {
        Team team = getTeam(teamId);

        Set<Task> tasks = taskPersistencePort.findTasksByTeam(team);
        return tasks;
    }

    @Override
    public Set<Task> getTasksByUserIdAndTeamId(String userId, String teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        Set<Task> tasks = taskPersistencePort.findTasksByUserAndTeam(user, team);
        return tasks;
    }

    private Team getTeam(String teamId) {
        Team team = teamService.getTeamByTeamId(teamId);
        if (team == null) {
            throw new TeamException();
        }
        return team;
    }

    @Override
    public Set<Task> getTasksByUserIdAndAccountId(String userId, String accountId) {
        User user = getUser(userId);
        Account account = getAccount(accountId);

        Set<Task> tasks = taskPersistencePort.findTasksByUserAndAccount(user, account);
        return tasks;
    }

    private Account getAccount(String accountId) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new TeamException();
        }
        return account;
    }

}
