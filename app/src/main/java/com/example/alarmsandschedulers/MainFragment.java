package com.example.alarmsandschedulers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.alarmsandschedulers.alarm.AlarmFragment;
import com.example.alarmsandschedulers.databinding.FragmentMainBinding;
import com.example.alarmsandschedulers.notification.NotificationFragment;
import com.example.alarmsandschedulers.schedule.JobSchedulerFragment;

public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private static Fragment resolveFragment(int viewID) {
        Fragment fragment = null;
        switch (viewID) {
            case R.id.button_main_notification:
                fragment = NotificationFragment.newInstance();
                break;
            case R.id.button_main_alarm:
                fragment = AlarmFragment.newInstance();
                break;
            case R.id.button_main_job_scheduler:
                fragment = JobSchedulerFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.buttonMainNotification.setOnClickListener(this);
        binding.buttonMainAlarm.setOnClickListener(this);
        binding.buttonMainJobScheduler.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_fragment, resolveFragment(v.getId()))
                .addToBackStack("Main Fragment")
                .commit();
    }
}
