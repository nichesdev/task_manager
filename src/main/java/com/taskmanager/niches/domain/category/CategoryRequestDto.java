package com.taskmanager.niches.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CategoryRequestDto {

    @NotBlank
    private String categoryName;
    @NotNull
    private Integer userId;

}
