package com.clashroyale.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardBattleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardBattleApiApplication.class, args);
        System.out.print("🎮 Card Battle API is running on http://localhost:8080 \n  Open postman and test my api!");
    }
}