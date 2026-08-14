package com.taskmanager.niches.domain.task.dto;

import com.taskmanager.niches.domain.task.model.Priority;
import com.taskmanager.niches.domain.task.model.StatusTaskEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaskRequestDto {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    private StatusTaskEntity status;
    private LocalDate dueDate;
    @NotNull
    private Integer userId;
    private Integer categoryId;

}
