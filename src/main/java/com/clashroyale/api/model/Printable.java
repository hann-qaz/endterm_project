package com.clashroyale.api.model;

/**
 * Printable interface - demonstrates ISP and default/static methods
 */
public interface Printable {
    /**
     * Abstract method - must be implemented
     */
    String toFormattedString();

    /**
     * Default method - provides default implementation
     */
    default void printDetails() {
        System.out.println("📄 " + toFormattedString());
    }

    /**
     * Static method - utility method
     */
    static String formatHeader(String title) {
        return "===== " + title.toUpperCase() + " =====";
    }
}