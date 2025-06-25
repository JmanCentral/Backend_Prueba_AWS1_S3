package com.WishApp.WishApp.controllers;
import com.WishApp.WishApp.http.request.LoginRequestDTO;
import com.WishApp.WishApp.http.response.LoginResponseDTO;
import com.WishApp.WishApp.services.login.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LoginController {

    private final ILoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = loginService.userAutentication(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }


}
