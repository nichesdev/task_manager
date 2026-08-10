package com.taskmanager.niches.domain.task.repository;

import com.taskmanager.niches.domain.task.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    Optional<TaskEntity> findByTitleAndUserId(String title, Integer userId);
}
