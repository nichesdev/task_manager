package com.taskmanager.niches.domain.category;

import com.taskmanager.niches.domain.users.UserEntity;
import com.taskmanager.niches.domain.users.UserRepository;
import com.taskmanager.niches.exception.BadRequestException;
import com.taskmanager.niches.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto, String userEmail) throws NotFoundException, BadRequestException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        CategoryEntity existingCategory = categoryRepository
                .findByNameAndUserId(categoryRequestDto.getCategoryName(), user.getId())
                .orElse(null);
        if (existingCategory != null) {
            throw new BadRequestException("Já existe uma categoria com este titulo para este Usuário.");
        }
        CategoryEntity category = CategoryEntity.builder()
                .name(categoryRequestDto.getCategoryName())
                .user(user)
                .build();
        CategoryEntity savedCategory = categoryRepository.save(category);

        return CategoryResponseDto.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .userId(savedCategory.getUser().getId())
                .build();
    }
}
