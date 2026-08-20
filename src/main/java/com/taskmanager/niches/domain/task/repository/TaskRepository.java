package com.taskmanager.niches.domain.task.repository;

import com.taskmanager.niches.domain.task.model.Priority;
import com.taskmanager.niches.domain.task.model.StatusTaskEntity;
import com.taskmanager.niches.domain.task.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    Optional<TaskEntity> findByTitleAndUserId(String title, Integer userId);
    Optional<TaskEntity> findByIdAndUserId(Integer id, Integer userId);

    List<TaskEntity> findAllByUserId(Integer userId);
    List<TaskEntity> findAllByUserIdAndCategoryId(Integer userId, Integer categoryId);
    List<TaskEntity> findAllByUserIdAndStatus(Integer userId, StatusTaskEntity status);
    List<TaskEntity> findAllByUserIdAndPriority(Integer userId, Priority priority);

}
