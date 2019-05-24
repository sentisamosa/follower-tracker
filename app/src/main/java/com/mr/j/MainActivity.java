package com.mr.j;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString(Constants.userIdKey, getSharedPreferencesValue(Constants.userIdKey));

        return new JobInfo.Builder(281192, componentName)
                .setPeriodic(15 * 60 * 100)
                .setExtras(bundle)
                .setPersisted(true)
                .build();
    }

    /*----methods----*/
    private String getSharedPreferencesValue(String key) {
        try {
            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } catch (NullPointerException exception) {
            return "";
        }
    }
}