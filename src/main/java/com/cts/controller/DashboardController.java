package com.cts.controller;

import com.cts.model.User;
import com.cts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<List> getDashboard() {
        List<User> usersList = userRepository.findAll();
        List<String>  user = usersList.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        return ResponseEntity.ok(user);
    }
}
