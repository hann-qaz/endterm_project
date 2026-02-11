package com.clashroyale.api.patterns.singleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//SINGLETON PATTERN - Simple Logger
public class LoggerService {

    // Eager initialization - thread-safe без synchronized
    private static final LoggerService INSTANCE = new LoggerService();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Private constructor
    private LoggerService() {
        // Prevent instantiation
    }

    // Public accessor
    public static LoggerService getInstance() {
        return INSTANCE;
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    public void debug(String message) {
        log("DEBUG", message);
    }

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println(String.format("[%s] [%s] %s", timestamp, level, message));
    }
}