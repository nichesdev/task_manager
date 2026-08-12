package com.taskmanager.niches.domain.task.controller;

import com.taskmanager.niches.domain.task.dto.TaskRequestDto;
import com.taskmanager.niches.domain.task.dto.TaskResponseDto;
import com.taskmanager.niches.domain.task.service.TaskService;
import com.taskmanager.niches.exception.BadRequestException;
import com.taskmanager.niches.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) throws BadRequestException, NotFoundException {
        return taskService.createTask(taskRequestDto);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponseDto>> getAllByUserId(@PathVariable Integer userId) throws NotFoundException {
        List<TaskResponseDto> tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponseDto>> getByUserIdAndCategory(@PathVariable Integer userId, @PathVariable Integer categoryId) throws NotFoundException {
        List<TaskResponseDto> tasks = taskService.findAllByUserIdAndCategoryId(userId,categoryId);
        return  ResponseEntity.ok(tasks);
    }
}
