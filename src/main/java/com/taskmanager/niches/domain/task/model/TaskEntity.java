package com.taskmanager.niches.domain.task.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private StatusTaskEntity status;
}
