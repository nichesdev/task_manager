package com.taskmanager.niches.domain.users;


import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RolesEntity implements GrantedAuthority {

    @Id
    private Integer id;
    private String name;

    @Override
    public @Nullable String getAuthority() {
        return name;
    }

}
