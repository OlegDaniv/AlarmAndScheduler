package com.example.alarmsandschedulers.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.alarmsandschedulers.NotificationConfig;
import com.example.alarmsandschedulers.R;
import com.example.alarmsandschedulers.databinding.FragmentAlarmBinding;
import static android.content.Context.ALARM_SERVICE;

public class AlarmFragment extends Fragment {

    private FragmentAlarmBinding binding;
    private AlarmManager alarmManager;
    private NotificationConfig config;

    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        config = new NotificationConfig(requireActivity());
        Intent notifyIntent = new Intent(getActivity(), AlarmReceiver.class);
        binding.toggleButtonAlarm.setChecked(
                (PendingIntent.getBroadcast(getContext(),
                        NotificationConfig.NOTIFICATION_ID,
                        notifyIntent, PendingIntent.FLAG_NO_CREATE) != null));
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (getContext(), NotificationConfig.NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        binding.toggleButtonAlarm.setOnCheckedChangeListener((buttonView, isChecked) ->
                startAlarm(isChecked, notifyPendingIntent));
        return binding.getRoot();
    }

    private void startAlarm(boolean isChecked, PendingIntent pendingIntent) {
        alarmManager = (AlarmManager) requireActivity().getSystemService(ALARM_SERVICE);
        String toastMessage;
        if (isChecked) {
            long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
            long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime, repeatInterval, pendingIntent);
            }
            toastMessage = getString(R.string.alarm_toast_when_button_on);
        } else {
            config.cancelAllNotifications();
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
            toastMessage = getString(R.string.alarm_toast_when_button_off);
        }
        Toast.makeText(getActivity(), toastMessage,
                Toast.LENGTH_SHORT).show();
    }
}
