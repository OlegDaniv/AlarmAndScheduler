package com.example.alarmsandschedulers.notification;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.alarmsandschedulers.NotificationConfig;
import com.example.alarmsandschedulers.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {

    private final NotificationReceiver receiver = new NotificationReceiver();
    private FragmentNotificationBinding binding;
    private NotificationConfig notificationConfig;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        notificationConfig = new NotificationConfig(requireActivity());
        notificationConfig.createNotificationChannel();
        setNotificationButtonState(true, false, false);
        binding.buttonNotificationFragmentCreate.setOnClickListener(v -> {
            notificationConfig.sendNotification();
            setNotificationButtonState(false, true, true);
        });
        binding.buttonNotificationFragmentUpdate.setOnClickListener(v -> {
            notificationConfig.updateNotification();
            setNotificationButtonState(false, false, true);
        });
        binding.buttonNotificationFragmentCancel.setOnClickListener(v -> {
            notificationConfig.cancelNotification();
            setNotificationButtonState(true, false, false);
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().registerReceiver(receiver, new IntentFilter(NotificationConfig.ACTION_UPDATE_NOTIFICATION));
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(receiver);
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        binding.buttonNotificationFragmentCreate.setEnabled(isNotifyEnabled);
        binding.buttonNotificationFragmentUpdate.setEnabled(isUpdateEnabled);
        binding.buttonNotificationFragmentCancel.setEnabled(isCancelEnabled);
    }
}
