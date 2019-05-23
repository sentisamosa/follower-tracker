package com.mr.j;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;

public class TrackerService extends Service {

    Handler handler;
    Runnable runnable;
    String userId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Todo get the count of the followers for the current user and save the count in SQLite DB or shared preferences(preferred).
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        userId = intent.getStringExtra(Constants.userIdKey);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                getFollowerCount();
            }
        };

//        handler.postDelayed(runnable, )
        return START_STICKY;
    }

    private int getFollowerCount() {
        GitHubAPICaller caller = new GitHubAPICaller(getBaseContext());

        caller.getResponse(String.format(Constants.userUrl, userId), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                try {
                    JSONObject userObj = result.getJSONObject(0);
                    int count = userObj.getInt(Constants.getFollowersCountKey);

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
        return 0;
    }

}
