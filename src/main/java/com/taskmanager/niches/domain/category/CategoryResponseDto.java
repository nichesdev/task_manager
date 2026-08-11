package com.taskmanager.niches.domain.category;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CategoryResponseDto {

    private Integer id;
    private String name;
    private Integer userId;
}
