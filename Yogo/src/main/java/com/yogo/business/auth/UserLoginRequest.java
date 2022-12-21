package com.yogo.business.auth;

import com.yogo.validation.EmailRegex;
import com.yogo.validation.PasswordRegex;
import lombok.Data;

@Data
public class UserLoginRequest {
    @EmailRegex
    private String email;

    @PasswordRegex
    private String password;
}
