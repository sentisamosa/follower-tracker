package com.mr.j;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class TrackerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        //Todo do everything that the service was supposed to do.
        //Todo use this link https://blog.teamtreehouse.com/scheduling-work-jobscheduler
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //Todo Think how to use this method. This method will be called by the Android system when the job is stopped in the mid.
        return false;
    }
}
