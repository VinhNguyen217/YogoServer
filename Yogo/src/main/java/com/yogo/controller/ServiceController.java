package com.yogo.controller;


import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.service.SerService;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private SerService serService;

    @GetMapping()
    public ResponseEntity<?> getServices() {
        return ResponseMessage.success(serService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseMessage.success(serService.getById(id));
    }
}
