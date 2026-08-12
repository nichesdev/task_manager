package com.taskmanager.niches.domain.task.service;

import com.taskmanager.niches.domain.task.dto.TaskRequestDto;
import com.taskmanager.niches.domain.task.dto.TaskResponseDto;
import com.taskmanager.niches.domain.task.model.TaskEntity;
import com.taskmanager.niches.domain.task.repository.TaskRepository;
import com.taskmanager.niches.domain.users.UserEntity;
import com.taskmanager.niches.domain.users.UserRepository;
import com.taskmanager.niches.exception.BadRequestException;
import com.taskmanager.niches.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) throws NotFoundException, BadRequestException {
        UserEntity user = userRepository.findById(taskRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User não encontrado."));

        TaskEntity existingTask = taskRepository
                .findByTitleAndUserId(taskRequestDto.getTitle(), taskRequestDto.getUserId())
                .orElse(null);
        if (existingTask != null) {
            throw new BadRequestException("Já existe uma Tarefa com este titulo para este Usúario.");
        }
        TaskEntity task = TaskEntity.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .priority(taskRequestDto.getPriority())
                .status(taskRequestDto.getStatus())
                .dueDate(taskRequestDto.getDueDate())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        TaskEntity savedTask = taskRepository.save(task);

        return TaskResponseDto.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .priority(savedTask.getPriority())
                .status(savedTask.getStatus())
                .dueDate(savedTask.getDueDate())
                .createdDate(savedTask.getCreatedDate())
                .userId(savedTask.getUser().getId())
                .build();
    }
    public List<TaskResponseDto> findAllByUserId(Integer userId) throws NotFoundException {
        if(!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        List<TaskEntity> tasks = taskRepository.findAllByUserId(userId);

        return tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .dueDate(task.getDueDate())
                        .createdDate(task.getCreatedDate())
                        .userId(task.getUser().getId())
                        .build())
                .toList();
    }
}
