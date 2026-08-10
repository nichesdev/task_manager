package com.taskmanager.niches.domain.users;

import com.taskmanager.niches.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRequestDto userRequestDto) throws BadRequestException {
        UserEntity user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElse(null);
        if (user != null) {
            throw new BadRequestException("Username já cadastrado com este Email.");
        }

        userRepository.save(UserEntity.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build());
    }
}
