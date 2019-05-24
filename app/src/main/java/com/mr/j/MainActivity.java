package com.mr.j;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    JobScheduler jobScheduler;

    private Switch.OnCheckedChangeListener toggleJob = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int resultCode = jobScheduler != null ? jobScheduler.schedule(getJobInfo()) : 0;
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Toast.makeText(MainActivity.this, "Tracker Started", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error starting tracker", Toast.LENGTH_SHORT).show();
                }

            } else {
                jobScheduler.cancelAll();
                Toast.makeText(MainActivity.this, "Tracker stopped", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        Switch jobSwitch = findViewById(R.id.service_switch);
        jobSwitch.setOnCheckedChangeListener(toggleJob);
    }

    JobInfo getJobInfo() {
        ComponentName componentName = new ComponentName(MainActivity.this, TrackerService.class);

        return new JobInfo.Builder(281192, componentName)
//                        .setPeriodic(4 * 60 * 60 * 100)
                .setPeriodic(30 * 60 * 100)
                .setPersisted(true)
                .build();
    }
}