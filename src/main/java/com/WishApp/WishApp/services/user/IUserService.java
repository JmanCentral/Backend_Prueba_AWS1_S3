package com.WishApp.WishApp.services.user;

import com.WishApp.WishApp.http.request.UserRequestDTO;
import com.WishApp.WishApp.http.response.UserResponseDTO;

public interface IUserService {

    public UserResponseDTO save(UserRequestDTO userRequestDTO);

}
