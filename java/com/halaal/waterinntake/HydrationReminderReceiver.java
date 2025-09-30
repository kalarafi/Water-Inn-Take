package com.halaal.waterinntake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HydrationReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        User user = SharedPrefManager.getUser(context);
        float intake = WaterDataManager.getTodayWaterIntake(context);
        float goal = (float) user.dailyWaterGoal;

        long now = System.currentTimeMillis();
        long dayProgress = now % (24 * 60 * 60 * 1000L); // ms since midnight
        float expected = (goal * dayProgress) / (24 * 60 * 60 * 1000L); // proportion of goal

        if (intake < expected * 0.8f) { // behind by more than 20%
            NotificationHelper.sendHydrationReminder(
                    context,
                    "Hydration Reminder",
                    "You're falling behind! Drink some water 💧"
            );
        }
    }
}