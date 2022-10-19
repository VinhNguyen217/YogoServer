package com.yogo.service;

import java.util.List;

import com.yogo.model.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.repository.FunctionRepository;

@Service
public class FunctionService {

    @Autowired
    private FunctionRepository functionRepository;

    public List<Function> getAll() {
        return functionRepository.findAll();
    }

    public Function getById(Integer id) {
        return functionRepository.findById(id).get();
    }
}
