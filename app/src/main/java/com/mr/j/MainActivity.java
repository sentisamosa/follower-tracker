package com.mr.j;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    CustomAdapter followersListAdapter;
    ListView followersList;
    int pageNo, followingPaging = 1;

    /*----Lifecycle methods----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstRun()) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.userid_dialog);
            dialog.setTitle(Constants.dialogTitle);

            final EditText userId = dialog.findViewById(R.id.userId_EditTxt);
            Button setButton = dialog.findViewById(R.id.setButton);

            setButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editSharedPreferences(Constants.userIdKey, userId.getText().toString());
                    Toast.makeText(MainActivity.this, "UserId set", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getFollowers();

                }
            });

            Toast.makeText(MainActivity.this, R.string.user_id_toast, Toast.LENGTH_LONG).show();
            dialog.show();
        }

        initializeComponents();
    }


    /*----methods----*/
    private String getSharedPreferencesValue(String key) {
        sharedPreferences = getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    private <T> void editSharedPreferences(String keyName, T value) {

        sharedPreferences = getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String)
            editor.putString(keyName, value.toString());
        else
            editor.putBoolean(keyName, Boolean.parseBoolean(value.toString()));
        editor.apply();
    }

    private boolean isFirstRun() {
        sharedPreferences = getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Constants.isFirstRun)) {
            editSharedPreferences(Constants.isFirstRun, true);
            return false;
        }

        return true;
    }

    private void initializeComponents() {
        try {
            Constants.userIdValue = getSharedPreferencesValue(Constants.userIdKey);
        } catch (Exception e) {
            Log.e("Error", "initializeComponents: " + e.getMessage());
        }

        followersList = findViewById(R.id.followersList);
        followersList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = followersList.getCount();

                if (scrollState == SCROLL_STATE_IDLE)
                    if (followersList.getLastVisiblePosition() >= count - threshold) {
                        pageNo++;
                        getFollowers();
                    }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }


    /*----API Calling methods----*/
    private void getFollowers() {
        GitHubAPICaller caller = new GitHubAPICaller(MainActivity.this);
        caller.getResponse(String.format(Constants.followerUrl, Constants.userIdValue, pageNo), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                populateFollowers(result);
            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImageResponse(Bitmap image) {

            }
        });
    }

    private void getFollowing() {
        GitHubAPICaller caller = new GitHubAPICaller(MainActivity.this);
        caller.getResponse(String.format(Constants.followingUrl, Constants.userIdValue, followingPaging), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {

            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImageResponse(Bitmap image) {

            }
        });
    }

    private void populateFollowers(JSONArray jsonArray) {
        try {
            ArrayList<UserItem> users = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tempObj = jsonArray.getJSONObject(i);
                users.add(new UserItem(tempObj.getString(Constants.gitHubUsernameKey), tempObj.getString(Constants.getGitHubUserIdKey), tempObj.getString(Constants.getGitHubUserImageUrl)));
            }

            if (followersListAdapter == null) {
                followersListAdapter = new CustomAdapter(MainActivity.this, R.layout.user_list, users);
                followersList.setAdapter(followersListAdapter);
            } else {
                followersListAdapter.addAll(users);
                followersListAdapter.notifyDataSetChanged();
            }
        } catch (JSONException exception) {
            Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void populateFollowing(JSONArray jsonArray) {
        try {
            ArrayList<UserItem> users = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tempObj = jsonArray.getJSONObject(i);
                users.add(new UserItem(tempObj.getString(Constants.gitHubUsernameKey), tempObj.getString(Constants.getGitHubUserIdKey), tempObj.getString(Constants.getGitHubUserImageUrl)));
            }
        } catch (JSONException exception) {
            Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}