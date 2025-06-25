package com.WishApp.WishApp.services.login;

import com.WishApp.WishApp.http.request.LoginRequestDTO;
import com.WishApp.WishApp.http.response.LoginResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;


public interface ILoginService  {

    LoginResponseDTO userAutentication(LoginRequestDTO loginRequestDTO);

    UserDetails loadUserByUsername(String username);
}
