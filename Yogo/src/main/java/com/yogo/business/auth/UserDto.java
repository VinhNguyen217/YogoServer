package com.yogo.business.auth;

import com.yogo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String username;
    private String email;
    private String phone;
    private String address;
    private Role role;
}
