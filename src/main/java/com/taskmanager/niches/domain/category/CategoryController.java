package com.taskmanager.niches.domain.category;

import com.taskmanager.niches.exception.BadRequestException;
import com.taskmanager.niches.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) throws NotFoundException, BadRequestException {
        return categoryService.createCategory(categoryRequestDto);
    }
}
