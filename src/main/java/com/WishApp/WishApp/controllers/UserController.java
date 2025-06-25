package com.WishApp.WishApp.controllers;

import com.WishApp.WishApp.http.request.UserRequestDTO;
import com.WishApp.WishApp.http.response.UserResponseDTO;
import com.WishApp.WishApp.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.save(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

}
