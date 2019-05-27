package com.mr.j;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

class Constants {

    static String PREFERENCE_NAME = "myPreferences";
    //    static String isFirstRun = "IsFirstRun";
    static String DIALOG_TITLE = "Enter User ID";
    static String USER_ID_KEY = "UserId";
    static String FOLLOWER_COUNT = "followersCount";
//    static String userIdValue = "";

    /*-----API call related constants-----*/
    static String FOLLOWER_URL = "https://api.github.com/users/%s/followers?page=%d";
    static String FOLLOWING_URL = "https://api.github.com/users/%s/following?page=%d";
    static String USER_URL = "https://api.github.com/users/%s";
    static String GET_GITHUB_USERNAME_KEY = "login";
    static String GET_GITHUB_USER_ID_KEY = "id";
    static String GET_FOLLOWERS_COUNT_KEY = "followers";
    static String GET_GITHUB_USER_IMAGE_URL = "avatar_url";


    static int JOB_ID = 281192;

    /*-----Database related constants-----*/
    static String DATABASE_NAME = "follower_tracker";
    static String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS followers(Username VARCHAR);";
    static String QUERY_CREATE_COUNT_TABLE = "CREATE TABLE IF NOT EXISTS counts(Count INTEGER);";
    static String QUERY_INSERT_IN_FOLLOWERS = "INSERT INTO followers(Username) VALUES ";

    static String QUERY_INSERT_IN_COUNTS = "INSERT INTO counts(Count) VALUES (%d);";
    static String QUERY_COUNT_ROWS = "SELECT COUNT() FROM followers;";


    /*-------Static methods---------*/
    static <T> void editSharedPreferences(String keyName, T value, Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof String)
                editor.putString(keyName, value.toString());
            else
                editor.putBoolean(keyName, Boolean.parseBoolean(value.toString()));
            editor.apply();
        } catch (NullPointerException exception) {
            Toast.makeText(context, "Error occurred while editing shared preferences", Toast.LENGTH_SHORT).show();
        }
    }

    static String getSharedPreferencesValue(String key, Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } catch (NullPointerException exception) {
            return "";
        }
    }

}
