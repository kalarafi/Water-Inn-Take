package com.halaal.waterinntake;

public class WaterUtils {

    public static double calculateWaterGoal(String gender, int age, int height, int weight) {
        // Basic logic: 35 ml per kg of body weight
        double base = weight * 35; // in ml

        // Add adjustments based on age or gender if needed
        if (gender.equalsIgnoreCase("female")) {
            base -= 200; // Less muscle mass, slightly lower baseline
        }

        return base / 1000.0; // Convert to litres
    }

    public static String getTemperatureAdvice(int temperature) {
        if (temperature <= 10) {
            return "Drink at least 1.5–2 litres of water daily.";
        } else if (temperature <= 20) {
            return "Aim for 2–2.5 litres of water per day.";
        } else if (temperature <= 30) {
            return "Drink around 2.5–3 litres to stay well hydrated.";
        } else if (temperature <= 40) {
            return "It’s hot! Try to drink 3.5–4 litres.";
        } else {
            return "Extreme heat! Drink 4+ litres and monitor your hydration closely.";
        }
    }
}
