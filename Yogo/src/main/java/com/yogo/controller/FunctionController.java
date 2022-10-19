package com.yogo.controller;

import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.service.FunctionService;

@RestController
@RequestMapping("/services")
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseMessage.success(functionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseMessage.success(functionService.getById(id));
    }
}
