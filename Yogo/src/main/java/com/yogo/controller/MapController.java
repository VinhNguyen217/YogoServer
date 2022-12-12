package com.yogo.controller;

import java.io.IOException;

import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.service.MapService;
import com.yogo.business.auth.UserService;


@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @Autowired
    private UserService userService;

    @PostMapping("/point")
    public ResponseEntity<?> findPoint(@RequestHeader(value = "session") String sessionKey,
                                       @RequestParam("p") String point) throws IOException {
        return ResponseMessage.success(mapService.findPoint(point));
    }

    @PostMapping("/route")
    public ResponseEntity<?> findRoute(@RequestHeader(value = "session") String sessionKey,
                                       @RequestParam String p0, @RequestParam String p1) throws IOException {

//        if (userService.isSessionValid(sessionKey) != null) {
        return ResponseMessage.success(mapService.findRoute(p0, p1));
//        }
//        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }

    @PostMapping("/point_name")
    public ResponseEntity<?> findPointName(@RequestHeader(value = "session") String sessionKey,
                                           @RequestParam Double lat, @RequestParam Double lon) throws IOException {
//        if (userService.isSessionValid(sessionKey) != null) {
        return ResponseMessage.success(mapService.findPointName(lat, lon));
//        }
//        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }
}
