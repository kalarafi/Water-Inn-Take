package com.halaal.waterinntake;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WaterDataManager {

    private static final String PREF_NAME = "WaterIntakePrefs";

    public static void logWaterIntake(Context context, float amountInLitres) {
        String today = getTodayKey();
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        float current = prefs.getFloat(today, 0f);
        prefs.edit().putFloat(today, current + amountInLitres).apply();
    }

    public static float getTodayWaterIntake(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getFloat(getTodayKey(), 0f);
    }

    public static void resetTodayIntake(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putFloat(getTodayKey(), 0f).apply();
    }

    public static String getTodayKey() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
