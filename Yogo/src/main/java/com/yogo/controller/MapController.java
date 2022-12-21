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

import com.yogo.business.map.MapService;
import com.yogo.business.auth.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @PostMapping("/point")
    public ResponseEntity<?> findPoint(@RequestParam("p") String point,
                                       HttpServletRequest servletRequest) throws IOException {
        return ResponseMessage.success(mapService.findPoint(point, servletRequest));
    }

    @PostMapping("/route")
    public ResponseEntity<?> findRoute(@RequestParam String p0,
                                       @RequestParam String p1,
                                       HttpServletRequest servletRequest) throws IOException {
        return ResponseMessage.success(mapService.findRoute(p0, p1, servletRequest));
    }

    @PostMapping("/point_name")
    public ResponseEntity<?> findPointName(@RequestParam Double lat,
                                           @RequestParam Double lon,
                                           HttpServletRequest servletRequest) throws IOException {
        return ResponseMessage.success(mapService.findPointName(lat, lon, servletRequest));
    }
}
