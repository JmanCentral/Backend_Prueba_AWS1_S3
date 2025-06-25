package com.WishApp.WishApp.mappers.interfacesUser;
import com.WishApp.WishApp.entities.User;
import com.WishApp.WishApp.http.request.UserRequestDTO;
import com.WishApp.WishApp.http.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
    }

    @Override
    public UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRol().stream()
                        .map(role -> role.getName().name())
                        .toList())
                .build();
    }




}

