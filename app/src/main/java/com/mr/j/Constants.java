package com.mr.j;

import android.os.AsyncTask;

class Constants {

    static String PREFERENCE_NAME = "myPreferences";
//    static String isFirstRun = "IsFirstRun";
    static String DIALOG_TITLE = "Enter User ID";
    static String USER_ID_KEY = "UserId";
//    static String userIdValue = "";

    /*-----API call related constants-----*/
    static String FOLLOWER_URL = "https://api.github.com/users/%s/followers?page=%d";
    static String FOLLOWING_URL = "https://api.github.com/users/%s/following?page=%d";
    static String USER_URL = "https://api.github.com/users/%s";
    static String GITHUB_USERNAME_KEY = "login";
    static String GET_GITHUB_USER_ID_KEY = "id";
    static String GET_FOLLOWERS_COUNT_KEY = "followers";
    static String GET_GITHUB_USER_IMAGE_URL = "avatar_url";


    static int JOB_ID = 281192;

    /*-----Database related constants-----*/
    static String DATABASE_NAME = "follower_tracker";
    static String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS followers(Username VARCHAR);";
    static String QUERY_CREATE_COUNT_TABLE = "CREATE TABLE IF NOT EXISTS counts(Count INTEGER, Date DATETIME);";
    static String QUERY_INSERT_VALUE = "INSERT INTO followers('%s')";
    static String QUERY_COUNT_ROWS = "SELECT COUNT() FROM followers";

}
