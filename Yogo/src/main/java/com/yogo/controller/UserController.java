package com.yogo.controller;

import com.yogo.business.auth.UserLoginDto;
import com.yogo.business.auth.UserRegisterDto;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.User;
import com.yogo.business.auth.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> listAll() {
        return ResponseMessage.success(userService.listAll());
    }

    @PostMapping("/client/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody UserRegisterDto user) {
        return ResponseMessage.success(userService.createClient(user));
    }

    @PostMapping("/driver/register")
    public ResponseEntity<?> registerDriver(@Valid @RequestBody UserRegisterDto user) {
        return ResponseMessage.success(userService.createDriver(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return ResponseMessage.success(userService.login(userLoginDto));
    }
}
