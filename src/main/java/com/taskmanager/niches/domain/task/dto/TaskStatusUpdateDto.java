package com.taskmanager.niches.domain.task.dto;

import com.taskmanager.niches.domain.task.model.StatusTaskEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaskStatusUpdateDto {
    @NotNull
    private StatusTaskEntity status;
}

