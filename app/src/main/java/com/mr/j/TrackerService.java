package com.mr.j;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TrackerService extends JobService {

    String userId;
    GitHubAPICaller caller;
    int count;
    boolean shouldIncrement;

    @Override
    public boolean onStartJob(JobParameters params) {
        caller = new GitHubAPICaller(getBaseContext());
        shouldIncrement = true;

        PersistableBundle bundle = params.getExtras();
        userId = bundle.getString(Constants.USER_ID_KEY);
        getFollowerCount();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //Todo Think how to use this method. This method will be called by the Android system when the job is stopped in the mid.
        return false;
    }


    private void getFollowerCount() {
        caller.getResponse(String.format(Constants.USER_URL, userId), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                try {
                    JSONObject userObj = result.getJSONObject(0);
                    count = userObj.getInt(Constants.GET_FOLLOWERS_COUNT_KEY);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext());
                    mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
                    mBuilder.setContentTitle("Follower tracker");
                    mBuilder.setContentText("Hi, You have " + count + " followers on GitHub");

                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    // notificationID allows you to update the notification later on.
                    if (mNotificationManager != null) {
                        mNotificationManager.notify((int) (Math.random() * 100), mBuilder.build());
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
