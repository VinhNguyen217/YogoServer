package com.yogo.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.DriverManager;
import com.yogo.model.SessionManager;
import com.yogo.model.User;
import com.yogo.service.UserService;

import com.google.common.hash.Hashing;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> listAll() {
        return ResponseMessage.success(userService.listAll());
    }

    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseMessage.success(userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isIdValid(id)) {
            String idSession = Hashing.murmur3_32().hashString(String.valueOf(id), StandardCharsets.UTF_8).toString();
            User user = userService.get(id);
            map.put("session_id", idSession);
            map.put("info", user);
            SessionManager.getInstance().map.put(idSession, user);
            if (user.getType() == 'd') {
                DriverManager.getInstance().driverWait.add(user);
            }
            return ResponseMessage.success(map);
        } else {
            map.put("session_id", null);
            map.put("info", null);
            return ResponseMessage.success(map);
        }
    }

}
