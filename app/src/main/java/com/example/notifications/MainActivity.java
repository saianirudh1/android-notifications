package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button localNotification;
    private String CHANNEL_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Local Notification
        localNotification = findViewById(R.id.localNotification);

        localNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "2", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);

//              On Click of notification move to this Activity.
                Intent contentIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pendingContentIntent = PendingIntent.getActivity(MainActivity.this, 200, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//              Adding Action Buttons
                Intent actionIntent = new Intent(MainActivity.this, ActionReciever.class);
                actionIntent.putExtra("message", "This is a action button");
                PendingIntent pendingActionIntent = PendingIntent.getBroadcast(MainActivity.this, 300, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Action toastAction = new Notification.Action(R.drawable.ic_baseline_notifications_24, "Toast Message",pendingActionIntent);

//              Adding Dismiss Action Button
                Intent dismissIntent = new Intent(MainActivity.this, DismissReciever.class);
                PendingIntent pendingDismissIntent = PendingIntent.getBroadcast(MainActivity.this, 400, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Action dismissAction = new Notification.Action(R.drawable.ic_baseline_notifications_24, "Dismiss", pendingDismissIntent);

                Notification.Builder builder = new Notification.Builder(MainActivity.this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24)
                        .setContentTitle("This is a Local Notification")
                        .setContentText("This is the description of Local Notification")
                        .setAutoCancel(true)
                        .setContentIntent(pendingContentIntent)
                        .addAction(toastAction)
                        .addAction(dismissAction);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });

//      Periodic Notification
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}