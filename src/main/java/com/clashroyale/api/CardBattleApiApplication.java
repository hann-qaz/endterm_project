package com.clashroyale.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MAIN APPLICATION CLASS
 * Entry point for Spring Boot application
 */
@SpringBootApplication
public class CardBattleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardBattleApiApplication.class, args);
        System.out.println("🎮 Card Battle API is running on http://localhost:8080");
        System.out.println("📚 API Documentation: http://localhost:8080/api");
    }
}