package com.example.alarmsandschedulers.schedule;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.example.alarmsandschedulers.MainActivity;
import com.example.alarmsandschedulers.NotificationConfig;

public class JobSchedulerJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        NotificationConfig config = new NotificationConfig(getApplicationContext());
        config.createNotificationChannel();
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, 0, new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);
        config.notificationForSchedule(contentPendingIntent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
