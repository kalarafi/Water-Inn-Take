package com.halaal.waterinntake;

public class ReminderUtils {
    public static void scheduleHydrationReminder(Context context) {
        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(HydrationReminderWorker.class, 2, java.util.concurrent.TimeUnit.HOURS)
                        .build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "HydrationReminder",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );
    }
}
