package com.taskmanager.niches.domain.task.service;

import com.taskmanager.niches.domain.category.CategoryEntity;
import com.taskmanager.niches.domain.category.CategoryRepository;
import com.taskmanager.niches.domain.category.CategoryResponseDto;
import com.taskmanager.niches.domain.task.dto.TaskRequestDto;
import com.taskmanager.niches.domain.task.dto.TaskResponseDto;
import com.taskmanager.niches.domain.task.dto.TaskStatusUpdateDto;
import com.taskmanager.niches.domain.task.dto.TaskUpdateDto;
import com.taskmanager.niches.domain.task.model.Priority;
import com.taskmanager.niches.domain.task.model.StatusTaskEntity;
import com.taskmanager.niches.domain.task.model.TaskEntity;
import com.taskmanager.niches.domain.task.repository.TaskRepository;
import com.taskmanager.niches.domain.users.UserEntity;
import com.taskmanager.niches.domain.users.UserRepository;
import com.taskmanager.niches.exception.BadRequestException;
import com.taskmanager.niches.exception.NotFoundException;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, String userEmail) throws NotFoundException, BadRequestException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User não encontrado."));

        TaskEntity existingTask = taskRepository
                .findByTitleAndUserId(taskRequestDto.getTitle(), user.getId())
                .orElse(null);
        if (existingTask != null) {
            throw new BadRequestException("Já existe uma Tarefa com este titulo para este Usúario.");
        }

        CategoryEntity category = null;
        if (taskRequestDto.getCategoryId() != null) {
            category = categoryRepository.findByIdAndUserId(taskRequestDto.getCategoryId(), user.getId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
        }
        TaskEntity task = TaskEntity.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .priority(taskRequestDto.getPriority())
                .status(taskRequestDto.getStatus())
                .dueDate(taskRequestDto.getDueDate())
                .createdDate(LocalDateTime.now())
                .user(user)
                .category(category)
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
                .categoryId(savedTask.getCategory() != null ? savedTask.getCategory().getId() : null)
                .build();
    }

    public List<TaskResponseDto> findAllByUserId(Integer userId) throws NotFoundException {
        if (!userRepository.existsById(userId)) {
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

    public List<TaskResponseDto> findAllByUserIdAndCategoryId(Integer categoryId, String userEmail) throws NotFoundException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        CategoryEntity category = categoryRepository.findByIdAndUserId(categoryId, user.getId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada para este usuário"));

        List<TaskEntity> tasks = taskRepository.findAllByUserIdAndCategoryId(user.getId(), category.getId());

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
                        .categoryId(task.getCategory() != null ? task.getCategory().getId() : null)
                        .build())
                .toList();
    }

    public List<TaskResponseDto> findAllByUserIdAndStatus(Integer userId, StatusTaskEntity status) throws NotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuário não encontrado");
        }
        List<TaskEntity> tasks = taskRepository.findAllByUserIdAndStatus(userId, status);

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
                        .categoryId(task.getCategory() != null ? task.getCategory().getId() : null)
                        .build())
                .toList();
    }

    public List<TaskResponseDto> findAllByUserIdAndPriority(Integer userId, Priority priority) throws NotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuário não encontrado");
        }
        List<TaskEntity> tasks = taskRepository.findAllByUserIdAndPriority(userId, priority);

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
                        .categoryId(task.getCategory() != null ? task.getCategory().getId() : null)
                        .build())
                .toList();
    }

    public TaskResponseDto updateTask(Integer id, TaskUpdateDto dto, String userEmail) throws NotFoundException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        TaskEntity task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada."));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());

        if (dto.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findByIdAndUserId(dto.getCategoryId(), user.getId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        TaskEntity updatedTask = taskRepository.save(task);

        return TaskResponseDto.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .priority(updatedTask.getPriority())
                .status(updatedTask.getStatus())
                .dueDate(updatedTask.getDueDate())
                .createdDate(updatedTask.getCreatedDate())
                .userId(updatedTask.getUser().getId())
                .categoryId(updatedTask.getCategory() != null ? updatedTask.getCategory().getId() : null)
                .build();
    }

    public TaskResponseDto updateStatus(Integer id, TaskStatusUpdateDto dto, String userEmail) throws NotFoundException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        TaskEntity task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada."));

        task.setStatus(dto.getStatus());
        TaskEntity updatedStatus = taskRepository.save(task);

        return TaskResponseDto.builder()
                .id(updatedStatus.getId())
                .title(updatedStatus.getTitle())
                .description(updatedStatus.getDescription())
                .priority(updatedStatus.getPriority())
                .status(updatedStatus.getStatus())
                .dueDate(updatedStatus.getDueDate())
                .createdDate(updatedStatus.getCreatedDate())
                .userId(updatedStatus.getUser().getId())
                .categoryId(updatedStatus.getCategory() != null ? updatedStatus.getCategory().getId() : null)
                .build();
    }

    public void deleteTask(Integer id, String userEmail) throws NotFoundException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        TaskEntity task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada."));

        taskRepository.delete(task);
    }
}

