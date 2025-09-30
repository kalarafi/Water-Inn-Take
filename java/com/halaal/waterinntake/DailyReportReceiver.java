package com.halaal.waterinntake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyReportReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        User user = SharedPrefManager.getUser(context);
        float total = WaterDataManager.getTodayWaterIntake(context);

        String title = "Daily Water Report";
        String msg = "You drank " + total + "L today. "
                + (total >= user.dailyWaterGoal ? "Goal achieved! 🥤" : "Try to do better tomorrow 💧");

        NotificationHelper.sendHydrationReminder(context, title, msg);

        // Optional: Reset daily intake after sending report
        WaterDataManager.resetTodayIntake(context);
    }
}
