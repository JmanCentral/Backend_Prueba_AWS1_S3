package com.WishApp.WishApp.controllers;
import com.WishApp.WishApp.http.request.LoginRequestDTO;
import com.WishApp.WishApp.http.response.LoginResponseDTO;
import com.WishApp.WishApp.services.login.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final ILoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = loginService.userAutentication(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(loginService.refreshToken(authHeader));
    }



}
