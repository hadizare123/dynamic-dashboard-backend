package com.dashboard.dynamic_dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/api/ping")
    public Map<String, Object> ping() {
        return Map.of(
                "status", "ONLINE",
                "message", "Dynamic Dashboard Backend is up and running!",
                "developer", "Hadi",
                "engine", "Spring Boot 3.3.5 & Java 21"
        );
    }
}
