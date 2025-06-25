package com.WishApp.WishApp.services.user;
import com.WishApp.WishApp.entities.Rol;
import com.WishApp.WishApp.entities.User;
import com.WishApp.WishApp.entities.enums.ERole;
import com.WishApp.WishApp.excepciones.User.EmailAlreadyRegisteredException;
import com.WishApp.WishApp.excepciones.User.RolNotFoundException;
import com.WishApp.WishApp.excepciones.User.UsernameAlreadyRegisteredException;
import com.WishApp.WishApp.http.request.UserRequestDTO;
import com.WishApp.WishApp.http.response.UserResponseDTO;
import com.WishApp.WishApp.mappers.interfacesUser.UserMapper;
import com.WishApp.WishApp.persistencie.RolRepository;
import com.WishApp.WishApp.persistencie.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class IUserImpl implements IUserService {

    private final UserRepository userRepository;

    private final RolRepository rolRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UsernameAlreadyRegisteredException("El username  ya está registrado.");
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyRegisteredException("El correo ya está registrado.");
        }

        User user = userMapper.toEntity(userRequestDTO);

        Set<Rol> roles = userRequestDTO.getRol().stream()
                .map(rol -> {
                    log.info("Rol recibido como string: {}", rol);

                    ERole rolEnum;
                    try {
                        rolEnum = ERole.valueOf(rol);
                        log.info("Rol convertido a Enum: {}", rolEnum);
                    } catch (IllegalArgumentException e) {
                        log.error("Rol inválido: {}. No corresponde a ningún valor del enum ERole.", rol);
                        throw new RolNotFoundException("Rol inválido: " + rol);
                    }

                    Rol rolEntity = rolRepository.findByName(rolEnum)
                            .orElseThrow(() -> {
                                log.error("Rol no encontrado en base de datos: {}", rolEnum);
                                return new RolNotFoundException("Rol no encontrado: " + rolEnum);
                            });

                    log.info("Rol encontrado en DB: {}", rolEntity.getName());
                    return rolEntity;
                })
                .collect(Collectors.toSet());


        user.setRol(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);

    }

}
