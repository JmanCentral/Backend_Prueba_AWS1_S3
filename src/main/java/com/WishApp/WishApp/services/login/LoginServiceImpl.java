package com.WishApp.WishApp.services.login;

import com.WishApp.WishApp.config.JwtUtils;
import com.WishApp.WishApp.entities.Token;
import com.WishApp.WishApp.entities.User;
import com.WishApp.WishApp.entities.enums.TokenType;
import com.WishApp.WishApp.excepciones.User.IncorrectCredentialsException;
import com.WishApp.WishApp.http.request.LoginRequestDTO;
import com.WishApp.WishApp.http.response.LoginResponseDTO;
import com.WishApp.WishApp.persistencie.TokenRepository;
import com.WishApp.WishApp.persistencie.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService, UserDetailsService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;


    @Override
    public LoginResponseDTO userAutentication(LoginRequestDTO loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("El username proporcionado no existe"));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException("Credenciales incorrectas : intentalo de nuevo");
        }

        revokeAllUserTokens(user);

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        Token token = Token.builder()
                .token(refreshToken)
                .tokenType(TokenType.BEARER)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

        return new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                accessToken,
                refreshToken,
                "Autenticación exitosa"
        );
    }

    private void revokeAllUserTokens(User user){

        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        System.out.println("Usuario: " + user);
        // Imprimir los roles del usuario
        System.out.println("Roles desde Usuario: " + user.getRol());

        // Convertir los roles del usuario a authorities de Spring Security
        Collection<? extends GrantedAuthority> authorities = user.getRol().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());

        // Crear el objeto UserDetails
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        // Imprimir detalles del UserDetails
        System.out.println("UserDetails username: " + userDetails.getUsername());
        System.out.println("UserDetails password (hashed): " + userDetails.getPassword());
        System.out.println("UserDetails roles/authorities:");
        userDetails.getAuthorities().forEach(auth -> System.out.println(" - " + auth.getAuthority()));

        return userDetails;
    }

    @Override
    public LoginResponseDTO refreshToken(String authHeader) {
    return null;
    }

}
