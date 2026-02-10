package com.clashroyale.api.patterns.singleton;

/**
 * SINGLETON PATTERN - Database Configuration
 * Eager initialization (thread-safe)
 */
public class DatabaseConfig {

    // ✅ Eager initialization
    private static final DatabaseConfig INSTANCE = new DatabaseConfig();

    private String databaseUrl;
    private int maxConnections;

    // Private constructor
    private DatabaseConfig() {
        this.databaseUrl = "jdbc:postgresql://localhost:5432/card_battle_db";
        this.maxConnections = 10;
    }

    public static DatabaseConfig getInstance() {
        return INSTANCE;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
}