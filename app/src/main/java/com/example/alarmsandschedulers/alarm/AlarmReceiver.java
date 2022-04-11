package com.example.alarmsandschedulers.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.alarmsandschedulers.NotificationConfig;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationConfig config = new NotificationConfig(context);
        config.deliverNotification();
    }

}
