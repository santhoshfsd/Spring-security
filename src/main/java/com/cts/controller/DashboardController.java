package com.cts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @GetMapping
    public ResponseEntity<List> getDashboard() {
        List<String> response = new ArrayList<>();
        response.add("values-1");
        response.add("values-2");
        response.add("values-3");
        response.add("values-4");
        return ResponseEntity.ok(response);
    }
}
