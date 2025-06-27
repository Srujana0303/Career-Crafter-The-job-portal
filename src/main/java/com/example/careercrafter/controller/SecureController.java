package com.example.careercrafter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/secure")
public class SecureController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, Srujana! 🎉 This endpoint is secured by JWT.");
    }
}

