package com.taskmanager.niches.domain.category;


import com.taskmanager.niches.domain.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="categories")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
