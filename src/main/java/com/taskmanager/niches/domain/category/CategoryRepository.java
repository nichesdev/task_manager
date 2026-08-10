package com.taskmanager.niches.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByNameAndUserId(String name, Integer userId);

    List<CategoryEntity> findAllByUserId(Integer userId);
}
