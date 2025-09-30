package com.halaal.waterinntake;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class HydrationReminderAlarm {

    private static final int INTERVAL_HOURS = 2; // Customize as needed
    private static final int REQUEST_CODE = 202;

    public static void scheduleRepeatingReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HydrationReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);

        long intervalMillis = INTERVAL_HOURS * 60 * 60 * 1000L;
        long firstTrigger = System.currentTimeMillis() + intervalMillis;

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                firstTrigger,
                intervalMillis,
                pendingIntent
        );
    }

    public static void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HydrationReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }
}