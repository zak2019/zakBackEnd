package com.zak.application.service.api;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.Task;

import java.util.Optional;
import java.util.Set;

public interface TaskService {
    Optional<Task> createTask(String userId, Task task);
    Optional<Task> createTeamTask(String userId, String teamId, Task task);
    Set<Task> updateTaskPositionAndTaskList(String taskId, EventStatus newStatus, int toPosition);
    Set<Task> updateTaskPosition(String taskId, int toPosition);
    Optional<Task> updateTask(Task task);
    Task getTaskByTaskId(String taskId);
    Set<Task> getTasksByUserId(String userId);
    Set<Task> getTasksTeamId(String teamId);
    Set<Task> getTasksByUserIdAndTeamId(String userId, String teamId);
    Set<Task> getTasksByUserIdAndAccountId(String userId, String accountId);
    void deleteTaskByTaskId(String taskId);
}
