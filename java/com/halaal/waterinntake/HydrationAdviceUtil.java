package com.halaal.waterinntake;

public class HydrationAdviceUtil {

    public static String getAdviceForTemperature(int tempCelsius) {
        if (tempCelsius <= 10) {
            return "It's quite cold (0–10°C). You should drink at least 1.5–2 litres of water.";
        } else if (tempCelsius <= 20) {
            return "Cool weather (11–20°C). Aim for 2–2.5 litres daily.";
        } else if (tempCelsius <= 30) {
            return "Mild to warm (21–30°C). Stay hydrated with 2.5–3 litres.";
        } else if (tempCelsius <= 40) {
            return "Hot weather (31–40°C). Your intake should be 3–3.5 litres.";
        } else {
            return "Extreme heat (41–50°C). Ensure at least 4 litres of water to avoid dehydration.";
        }
    }
}