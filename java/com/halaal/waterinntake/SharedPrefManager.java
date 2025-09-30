package com.halaal.waterinntake;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "WaterPrefs";

    public static void saveUser(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", user.name);
        editor.putString("gender", user.gender);
        editor.putInt("age", user.age);
        editor.putInt("height", user.height);
        editor.putInt("weight", user.weight);
        editor.putFloat("dailyGoal", (float) user.dailyWaterGoal);
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                prefs.getString("name", ""),
                prefs.getString("gender", "Other"),
                prefs.getInt("age", 0),
                prefs.getInt("height", 0),
                prefs.getInt("weight", 0),
                prefs.getFloat("dailyGoal", 0f)
        );
    }
}