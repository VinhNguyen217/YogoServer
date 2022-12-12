package com.yogo.business.auth;

import com.yogo.validation.EmailRegex;
import com.yogo.validation.PasswordRegex;
import com.yogo.validation.PhoneRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @NotNull
    @NotBlank
    private String username;

    @EmailRegex
    private String email;

    @PhoneRegex
    private String phone;

    private String address;

    @PasswordRegex
    private String password;
}
