package com.WishApp.WishApp.config;

import com.WishApp.WishApp.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user;
        String username;
        String password;

        try {
            // Lee el cuerpo de la solicitud
            String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            System.out.println("Cuerpo de la solicitud: " + requestBody);

            // Convierte el JSON en un objeto Usuario
            user = new ObjectMapper().readValue(requestBody, User.class);
            username = user.getUsername();
            password = user.getPassword();

            System.out.println("Usuario deserializado: " + user);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Error al leer el cuerpo de la solicitud", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        // Crea el token de autenticación
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Autentica al usuario
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException, java.io.IOException {
        chain.doFilter(request, response);
    }

}
