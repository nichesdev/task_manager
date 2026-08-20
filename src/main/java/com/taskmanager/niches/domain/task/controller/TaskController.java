package com.taskmanager.niches.domain.task.controller;

import com.taskmanager.niches.domain.task.dto.TaskRequestDto;
import com.taskmanager.niches.domain.task.dto.TaskResponseDto;
import com.taskmanager.niches.domain.task.dto.TaskStatusUpdateDto;
import com.taskmanager.niches.domain.task.dto.TaskUpdateDto;
import com.taskmanager.niches.domain.task.model.Priority;
import com.taskmanager.niches.domain.task.model.StatusTaskEntity;
import com.taskmanager.niches.domain.task.service.TaskService;
import com.taskmanager.niches.exception.BadRequestException;
import com.taskmanager.niches.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto, Authentication authentication) throws BadRequestException, NotFoundException {
        String userEmail = authentication.getName();
        return taskService.createTask(taskRequestDto, userEmail);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponseDto>> getAllByUserId(@PathVariable Integer userId) throws NotFoundException {
        List<TaskResponseDto> tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @GetMapping("/user/{userId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponseDto>> getByUserIdAndCategory(@PathVariable Integer userId, @PathVariable Integer categoryId) throws NotFoundException {
        List<TaskResponseDto> tasks = taskService.findAllByUserIdAndCategoryId(userId,categoryId);
        return  ResponseEntity.ok(tasks);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @GetMapping("/user/{userId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponseDto>> getByUserIdAndStatus(@PathVariable Integer userId, @RequestParam StatusTaskEntity status) throws NotFoundException {
        List<TaskResponseDto> tasks = taskService.findAllByUserIdAndStatus(userId, status);
        return  ResponseEntity.ok(tasks);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @GetMapping("/user/{userId}/priority")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponseDto>> getByUserIdAndPriority(@PathVariable Integer userId, @RequestParam Priority priority) throws NotFoundException {
        List<TaskResponseDto> tasks = taskService.findAllByUserIdAndPriority(userId, priority);
        return  ResponseEntity.ok(tasks);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Integer id, @Valid @RequestBody TaskUpdateDto taskUpdateDto) throws NotFoundException {
        TaskResponseDto updatedTask = taskService.updateTask(id, taskUpdateDto);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskResponseDto> updateStatus(@PathVariable Integer id, @Valid @RequestBody TaskStatusUpdateDto taskStatusUpdateDto) throws NotFoundException {
        TaskResponseDto updatedTask = taskService.updateStatus(id, taskStatusUpdateDto);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) throws NotFoundException {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
