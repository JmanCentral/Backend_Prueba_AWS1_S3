package com.WishApp.WishApp.config;

import com.WishApp.WishApp.services.login.ILoginService;
import com.WishApp.WishApp.services.login.LoginServiceImpl;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Lazy
    private final ILoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        String tokenHeader  = request.getHeader("Authorization");


        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);

            if (jwtUtils.validateToken(token)){
                String username = jwtUtils.getUsername(token);
                UserDetails userDetails = loginService.loadUserByUsername(username);

                System.out.println("Roles del usuario '" + username + "':");

                userDetails.getAuthorities().forEach(auth -> System.out.println(auth.getAuthority()));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


        }
        filterChain.doFilter(request, response);

    }
}
