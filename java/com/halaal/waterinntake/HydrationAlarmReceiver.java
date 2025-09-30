package com.halaal.waterinntake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HydrationAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        User user = SharedPrefManager.getUser(context);
        float todayDrank = WaterDataManager.getTodayWaterIntake(context); // To be implemented

        if (todayDrank < user.dailyWaterGoal * 0.5) {
            NotificationHelper.sendHydrationReminder(context,
                    "You're behind on water!",
                    "Keep sipping! You've only reached " + todayDrank + "L out of " + user.dailyWaterGoal + "L.");
        } else {
            // Optional: silent encouragement or do nothing
        }
    }
}
