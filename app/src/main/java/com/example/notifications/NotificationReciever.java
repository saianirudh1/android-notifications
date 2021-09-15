package com.example.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReciever extends BroadcastReceiver {

    private String CHANNEL_ID = "2";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Periodic Notification", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Periodic Notification Title")
                .setContentText("This is a periodic Notification");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(2, builder.build());
    }
}
