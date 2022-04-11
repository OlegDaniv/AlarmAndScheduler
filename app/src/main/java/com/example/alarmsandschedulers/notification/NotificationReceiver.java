package com.example.alarmsandschedulers.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.alarmsandschedulers.NotificationConfig;

public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationConfig config = new NotificationConfig(context);
        config.createNotificationChannel();
        config.updateNotification();
    }
}