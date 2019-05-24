package com.mr.j;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrackerService extends JobService {

    Handler handler;
    Runnable runnable;
    String userId;

    @Override
    public boolean onStartJob(JobParameters params) {
        PersistableBundle bundle = params.getExtras();
        userId = bundle.getString(Constants.userIdKey);

        runnable = new Runnable() {
            @Override
            public void run() {
                getFollowerCount();
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 10 * 1000);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //Todo Think how to use this method. This method will be called by the Android system when the job is stopped in the mid.
        return false;
    }

    private void getFollowerCount() {
        GitHubAPICaller caller = new GitHubAPICaller(getBaseContext());

        caller.getResponse(String.format(Constants.userUrl, userId), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                try {
                    JSONObject userObj = result.getJSONObject(0);
                    int count = userObj.getInt(Constants.getFollowersCountKey);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext());
                    mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
                    mBuilder.setContentTitle("Notification Alert");
                    mBuilder.setContentText("Hi, You have " + count + " followers on GitHub");

                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    // notificationID allows you to update the notification later on.
                    if (mNotificationManager != null) {
                        mNotificationManager.notify(123, mBuilder.build());
                    }


                    //Todo if count < or > localCount, update local count and check what changed.
                    //Todo If changed, send a broadcast to a broadcast to check compare the saved and online list and generate notification of the user who un-followed

                } catch (JSONException ex) {
                    Log.e("TrackerService", "Error in JSON array response");
                }
            }

            @Override
            public void onErrorResponse(String error) {

            }

            @Override
            public void onImageResponse(Bitmap image) {

            }
        });
    }

}
