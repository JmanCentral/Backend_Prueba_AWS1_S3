package com.WishApp.WishApp.http.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> rol;
}
