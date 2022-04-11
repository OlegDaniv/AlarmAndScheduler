package com.example.alarmsandschedulers.schedule;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.alarmsandschedulers.R;
import com.example.alarmsandschedulers.databinding.FragmentJobSchedulerBinding;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class JobSchedulerFragment extends Fragment {

    private static final int JOB_ID = 0;
    private FragmentJobSchedulerBinding binding;
    private JobScheduler jobScheduler;

    public static JobSchedulerFragment newInstance() {
        return new JobSchedulerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJobSchedulerBinding.inflate(inflater, container, false);
        jobScheduler = (JobScheduler) requireActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        binding.buttonJobSchedulerStart.setOnClickListener(v -> schedulerJob());
        binding.buttonJobScheduleCancel.setOnClickListener(v -> cancelJob());
        binding.seekbarJobSchedulerSetTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    binding.textviewJobSchedulerSeekBarProcess.setText(progress + " s");
                } else {
                    binding.textviewJobSchedulerSeekBarProcess.setText(R.string.job_scheduler_textview_time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return binding.getRoot();
    }

    public void schedulerJob() {
        int selectedNetworkOption = necessaryNetworkType(binding.radioGroupJobSchedulerNetworkType.getCheckedRadioButtonId());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(requireActivity().getPackageName(),
                JobSchedulerJobService.class.getName()))
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(binding.switchJobSchedulerIdle.isChecked())
                .setRequiresCharging(binding.switchJobSchedulerCharging.isChecked());
        int seekBarInteger = binding.seekbarJobSchedulerSetTime.getProgress();
        boolean seekBarSet = seekBarInteger > 0;
        if (seekBarSet) {
            builder.setOverrideDeadline(seekBarInteger * 1000L);
        }
        if ((selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE)
                || binding.switchJobSchedulerCharging.isChecked()
                || binding.switchJobSchedulerIdle.isChecked()
                || seekBarSet) {
            JobInfo myJobInfo = builder.build();
            jobScheduler.schedule(myJobInfo);
            Toast.makeText(getContext(), R.string.job_scheduler_toast_text_when_constrains_are_met, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.job_scheduler_toast_text_when_constrain_not_met, Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelJob() {
        if (jobScheduler != null) {
            jobScheduler.cancelAll();
            jobScheduler = null;
            Toast.makeText(getContext(), R.string.job_scheduler_toast_text_when_canceled, Toast.LENGTH_SHORT).show();
        }
    }

    public int necessaryNetworkType(int selectedNetworkID) {
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        switch (selectedNetworkID) {
            case R.id.radio_button_job_scheduler_none:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.radio_button_job_scheduler_any:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.radio_button_job_scheduler_wifi:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }
        return selectedNetworkOption;
    }
}
