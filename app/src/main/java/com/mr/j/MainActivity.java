package com.mr.j;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    JobScheduler jobScheduler;

    private Switch.OnCheckedChangeListener toggleJob = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //Getting the followers count for the given user
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        GitHubAPICaller caller = new GitHubAPICaller(MainActivity.this);
                        try {
                            int count = caller.getFollowerCount(getSharedPreferencesValue(Constants.USER_ID_KEY));
                            DBHelper helper = new DBHelper(getApplicationContext());
                            helper.insertInCounts(count);
                            fillFollowersInDB(count);
                        } catch (Exception e) {
                            Log.e("Error", "Error in check changed");
                        }
                    }
                };
                thread.start();

                //Creating the job for tracking the followers
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

        if (isJobServiceOn())
            jobSwitch.setChecked(true);

        jobSwitch.setOnCheckedChangeListener(toggleJob);
    }

    JobInfo getJobInfo() {
        ComponentName componentName = new ComponentName(MainActivity.this, TrackerService.class);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString(Constants.USER_ID_KEY, getSharedPreferencesValue(Constants.USER_ID_KEY));

        return new JobInfo.Builder(Constants.JOB_ID, componentName)
                .setPeriodic(15 * 60 * 100)
                .setExtras(bundle)
                .setPersisted(true)
                .build();
    }

    /*----methods----*/
    private String getSharedPreferencesValue(String key) {
        try {
            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } catch (NullPointerException exception) {
            return "";
        }
    }

    private boolean isJobServiceOn() {
        JobScheduler scheduler = (JobScheduler) MainActivity.this.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        boolean hasBeenScheduled = false;

        if (scheduler != null) {
            for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
                if (jobInfo.getId() == Constants.JOB_ID) {
                    hasBeenScheduled = true;
                    break;
                }
            }
        }

        return hasBeenScheduled;

    }

    private void fillFollowersInDB(int count) {
        //Todo run a loop to count/30 + 1 and call the insertintofollowers everytime the caller returns list of users
    }
}