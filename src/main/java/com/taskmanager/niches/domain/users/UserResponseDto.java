package com.taskmanager.niches.domain.users;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;


}
