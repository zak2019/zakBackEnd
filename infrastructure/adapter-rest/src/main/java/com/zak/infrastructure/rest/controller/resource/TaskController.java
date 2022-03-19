package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.TaskService;
import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.Task;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.resource.dto.TaskDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/task")
public class TaskController {

    private final TaskService taskService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public TaskController(TaskService taskService, MapperFacade orikaMapperFacade) {
        this.taskService = taskService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/user/{userId}")
    public TaskDto createTask(
            @PathVariable String userId,
            @RequestBody TaskDto task) {
        Optional<Task> savedTask =
                taskService.createTask(
                        userId,
                        orikaMapperFacade.map(task, Task.class));
        return orikaMapperFacade.map(savedTask.get(), TaskDto.class);
    }

    @PostMapping("/user/{userId}/team/{teamId}")
    public TaskDto createTeamTask(
            @PathVariable String userId,
            @PathVariable String teamId,
            @RequestBody TaskDto task) {
        Optional<Task> savedTask =
                taskService.createTeamTask(
                        userId,
                        teamId,
                        orikaMapperFacade.map(task, Task.class));
        return orikaMapperFacade.map(savedTask.get(), TaskDto.class);
    }

    @PostMapping("/{taskId}/newStatus/{newStatus}/toPosition/{toPosition}")
    public Set<TaskDto> updateTaskPositionAndTaskList(
            @PathVariable String taskId,
            @PathVariable EventStatus newStatus,
            @PathVariable int toPosition) {
        Set<Task> updatedTasks =
                taskService.updateTaskPositionAndTaskList(
                        taskId,
                        newStatus,
                        toPosition);
        return orikaMapperFacade.mapAsSet(updatedTasks, TaskDto.class);
    }

    @PostMapping("/{taskId}/toPosition/{toPosition}")
    public Set<TaskDto> updateTaskPosition(
            @PathVariable String taskId,
            @PathVariable int toPosition) {
        Set<Task> updatedTasks =
                taskService.updateTaskPosition(
                        taskId,
                        toPosition);
        return orikaMapperFacade.mapAsSet(updatedTasks, TaskDto.class);
    }

    @PostMapping("/update")
    public TaskDto updateTask(
            @RequestBody TaskDto task) {
        Optional<Task> updatedTask =
                taskService.updateTask(
                        orikaMapperFacade.map(task, Task.class));
        return orikaMapperFacade.map(updatedTask.get(), TaskDto.class);
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskByTaskId(@PathVariable String taskId) {
        Task task = taskService.getTaskByTaskId(taskId);

        return orikaMapperFacade.map(task, TaskDto.class);
    }



    @GetMapping("/user/{userId}")
    public Set<TaskDto> getTasksByUserId(@PathVariable String userId) {
        Set<Task> tasks = taskService.getTasksByUserId(userId);

        return orikaMapperFacade.mapAsSet(tasks, TaskDto.class);
    }


    @GetMapping("/team/{teamId}")
    public Set<TaskDto> getTasksByTeamId(@PathVariable String teamId) {
        Set<Task> tasks = taskService.getTasksTeamId(teamId);

        return orikaMapperFacade.mapAsSet(tasks, TaskDto.class);
    }

    @GetMapping("/user/{userId}/team/{teamId}")
    public Set<TaskDto> getTasksByUserIdAndTeamId(@PathVariable String userId, @PathVariable String teamId) {
        Set<Task> tasks = taskService.getTasksByUserIdAndTeamId(userId, teamId);

        return orikaMapperFacade.mapAsSet(tasks, TaskDto.class);
    }

    @GetMapping("/user/{taskId}/account/{accountId}")
    public Set<TaskDto> getTasksByUserIdAndAccountId(@PathVariable String taskId, @PathVariable String accountId) {
        Set<Task> tasks = taskService.getTasksByUserIdAndAccountId(taskId, accountId);

        return orikaMapperFacade.mapAsSet(tasks, TaskDto.class);
    }


    @PostMapping("/{taskId}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
        taskService.deleteTaskByTaskId(taskId);
        return ResponseEntity.ok(new MessageResponseEntity("Task deleted successfully"));
    }

}
