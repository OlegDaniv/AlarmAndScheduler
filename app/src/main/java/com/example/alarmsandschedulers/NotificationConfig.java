package com.example.alarmsandschedulers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.core.app.NotificationCompat;
import com.example.alarmsandschedulers.notification.NotificationReceiver;
import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationConfig {

    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    public static final String ACTION_UPDATE_NOTIFICATION = "ACTION_UPDATE_NOTIFICATION";
    public static final int NOTIFICATION_ID = 0;
    private final Context activityContext;
    private NotificationManager notifyManager;

    public NotificationConfig(Context activityContext) {
        notifyManager = (NotificationManager) activityContext.getSystemService(NOTIFICATION_SERVICE);
        this.activityContext = activityContext;
        createNotificationChannel();

    }

    public void createNotificationChannel() {
        notifyManager = (NotificationManager) activityContext.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    activityContext.getString(R.string.notification_notification_channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(activityContext.getString(R.string.notification_notification_channel_description));
            notifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION)
                .setClass(activityContext, NotificationReceiver.class);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder()
                .addAction(R.drawable.notification_action_icon_update_notification,
                        activityContext.getString(R.string.notification_action_title), PendingIntent.getBroadcast(activityContext,
                                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT));
        notifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }

    public NotificationCompat.Builder getNotificationBuilder() {
        return new NotificationCompat.Builder(activityContext, PRIMARY_CHANNEL_ID)
                .setContentTitle(activityContext.getString(R.string.notification_small_notification_title))
                .setContentText(activityContext.getString(R.string.notification_notification_content_text))
                .setSmallIcon(R.drawable.notification_notification_icon_coffee)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(activityContext, NOTIFICATION_ID,
                        new Intent(activityContext, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
    }

    public void updateNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder()
                .setStyle(new NotificationCompat
                        .BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(activityContext.getResources(),
                                R.drawable.notification_big_picture_for_notification))
                        .setBigContentTitle(activityContext.getString(R.string.notification_big_notification_title)));
        notifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }

    public void cancelNotification() {
        notifyManager.cancel(NOTIFICATION_ID);
    }

    public void cancelAllNotifications() {
        notifyManager.cancelAll();
    }

    public void deliverNotification() {
        notifyManager = (NotificationManager) activityContext.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activityContext, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.job_scheduler_notification_icon)
                .setContentTitle(activityContext.getString(R.string.alarm_notification_title))
                .setContentText(activityContext.getString(R.string.alarm_notification_text))
                .setContentIntent(PendingIntent.getActivity(activityContext, NOTIFICATION_ID,
                        new Intent(activityContext, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void notificationForSchedule(PendingIntent pendingIntent) {
        notifyManager = (NotificationManager) activityContext.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activityContext, PRIMARY_CHANNEL_ID)
                .setContentTitle(activityContext.getString(R.string.job_scheduler_notification_title))
                .setContentText(activityContext.getString(R.string.job_scheduler_notification_text))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.job_scheduler_notification_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        notifyManager.notify(NOTIFICATION_ID, builder.build());

    }
}
