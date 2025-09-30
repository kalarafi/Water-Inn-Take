package com.halaal.waterinntake;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseSyncManager {

    public static void syncWaterDataIfOnline(Context context) {
        if (isOnline(context)) {
            User user = SharedPrefManager.getUser(context);
            float todayIntake = WaterDataManager.getTodayWaterIntake(context);
            String dateKey = WaterDataManager.getTodayKey();

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(user.name)
                    .child("records")
                    .child(dateKey);

            Map<String, Object> data = new HashMap<>();
            data.put("intake", todayIntake);
            data.put("goal", user.dailyWaterGoal);

            ref.setValue(data);
        }
    }

    private static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
