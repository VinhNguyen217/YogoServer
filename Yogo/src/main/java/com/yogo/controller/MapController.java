package com.yogo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.yogo.message.MessageText;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.Coordinates;
import com.yogo.service.MapService;
import com.yogo.service.UserService;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/maps")
public class MapController {

    @Autowired
    private MapService mapService;

    @Autowired
    private UserService userService;

    @PostMapping("/point")
    public ResponseEntity<?> findPoint(@RequestHeader(value = "session") String sessionKey,
                                       @RequestParam("p") String point) throws IOException {
        if (userService.isSessionValid(sessionKey) != null) {
            return ResponseMessage.success(mapService.findPoint(point));
        }
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }

    @PostMapping("/route")
    public ResponseEntity<?> findRoute(@RequestHeader(value = "session") String sessionKey,
                                       @RequestParam String p0, @RequestParam String p1) throws IOException {

        if (userService.isSessionValid(sessionKey) != null) {
            return ResponseMessage.success(mapService.findRoute(p0, p1));
        }
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }

    @PostMapping("/point_name")
    public ResponseEntity<?> findPointName(@RequestHeader(value = "session") String sessionKey,
                                           @RequestParam BigDecimal lat, @RequestParam BigDecimal lon) throws IOException {
        if (userService.isSessionValid(sessionKey) != null) {
            return ResponseMessage.success(mapService.findPointName(lat, lon));
        }
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }
}
