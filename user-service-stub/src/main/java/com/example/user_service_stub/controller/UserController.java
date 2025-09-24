package com.example.user_service_stub.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @PostMapping("/validate")
    public Map<String, Object> validateUser() {
        System.out.println("USER STUB: Validating user...");
        return Map.of("status", "success", "message", "User is valid");
    }
}