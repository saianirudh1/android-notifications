package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button localNotification;
    private String CHANNEL_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localNotification = findViewById(R.id.localNotification);

        localNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);

                Notification.Builder builder = new Notification.Builder(MainActivity.this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24)
                        .setContentTitle("This is a Local Notification")
                        .setContentText("This is the description of Local Notification");

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });

    }
}