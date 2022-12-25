package com.yogo.business.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserLoginResult {
    private String sessionId;
    private UserDto info;
}
