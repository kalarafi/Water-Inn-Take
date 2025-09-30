package com.halaal.waterinntake;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.*;

import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HydrationReminderWorker extends Worker {

    public HydrationReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        User user = SharedPrefManager.getUser(getApplicationContext());
        if (user == null) return Result.failure();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("WaterLogs")
                .child(user.name).child(today);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double total = snapshot.getValue(Double.class);
                if (total == null) total = 0.0;

                double expected = user.dailyWaterGoal * (getCurrentHour() / 24.0);
                if (total < expected - 0.2) {  // If falling behind by 200ml
                    sendNotification("Water Reminder", "You're falling behind your hydration goal. Drink up!");
                }

                if (total < expected - 0.4) {  // Danger level
                    sendNotification("⚠ Alarm: Drink Water Now!", "You're significantly behind today. Stay hydrated!", true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return Result.success();
    }

    private int getCurrentHour() {
        return Integer.parseInt(new SimpleDateFormat("H", Locale.getDefault()).format(new Date()));
    }

    private void sendNotification(String title, String message) {
        sendNotification(title, message, false);
    }

    private void sendNotification(String title, String message, boolean isAlarm) {
        String channelId = "WaterReminder";
        NotificationManager manager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Water Alerts",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_water)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (isAlarm) {
            builder.setSound(android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI);
        }

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
