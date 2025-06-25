package com.WishApp.WishApp.mappers.interfacesUser;

import com.WishApp.WishApp.entities.User;
import com.WishApp.WishApp.http.request.UserRequestDTO;
import com.WishApp.WishApp.http.response.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User toEntity(UserRequestDTO dto);
    UserResponseDTO toDTO(User user);
}
