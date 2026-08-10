package com.taskmanager.niches.domain.task.dto;


import com.taskmanager.niches.domain.task.model.Priority;
import com.taskmanager.niches.domain.task.model.StatusTaskEntity;
import com.taskmanager.niches.domain.users.UserResponseDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaskResponseDto {
    private Integer id;
    private String title;
    private String description;
    private Priority priority;
    private StatusTaskEntity status;
    private LocalDate dueDate;
    private LocalDate.now createdDate;
    private UserResponseDto user;
}
