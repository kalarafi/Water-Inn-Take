package com.halaal.waterinntake;

public class WaterGoalCalculator {

    public static double calculateDailyGoal(String gender, int age, int heightCm, int weightKg) {
        double base = weightKg * 0.03; // 30ml per kg baseline

        if ("Male".equalsIgnoreCase(gender)) {
            base += 0.5;
        } else if ("Female".equalsIgnoreCase(gender)) {
            base += 0.3;
        }

        if (age > 50) {
            base -= 0.2;
        }

        // Clamp between 1.5L and 5L
        return Math.max(1.5, Math.min(base, 5.0));
    }
}