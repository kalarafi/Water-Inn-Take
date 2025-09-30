package com.halaal.waterinntake;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class HydrationAlarmManager {

    public static void scheduleHydrationAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, HydrationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_IMMUTABLE);

        // Schedule first alarm 2 hours from now, repeat every 2 hours
        long interval = 2 * 60 * 60 * 1000L;
        long triggerAt = System.currentTimeMillis() + interval;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAt, interval, pendingIntent);
    }

    public static void cancelHydrationAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HydrationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }
}
