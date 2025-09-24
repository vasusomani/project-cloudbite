package com.example.restaurant_service_stub.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    @PostMapping("/check_stock")
    public Map<String, Object> checkStock() {
        System.out.println("RESTAURANT STUB: Checking stock...");
        return Map.of("status", "success", "message", "All items are in stock");
    }
}