package com.yogo.controller;


import com.yogo.business.travel_service.DistanceRequest;
import com.yogo.message.ResponseMessage;
import com.yogo.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yogo.business.travel_service.SerService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private SerService serService;

    @GetMapping()
    public ResponseEntity<?> getServices() {
        return ResponseMessage.success(serService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        return ResponseMessage.success(serService.getById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Service service) {
        return ResponseMessage.success(serService.insert(service));
    }

    @PostMapping("/distance")
    public ResponseEntity<?> calByDistance(@RequestBody DistanceRequest request, HttpServletRequest servletRequest) {
        return ResponseMessage.success(serService.calByDistance(request, servletRequest));
    }

}
