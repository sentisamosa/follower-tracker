package com.mr.j;

class Constants {

    static String preferenceName = "myPreferences";

//    static String isFirstRun = "IsFirstRun";

    static String dialogTitle = "Enter User ID";

    static String userIdKey = "UserId";

//    static String userIdValue = "";

    static String followerUrl = "https://api.github.com/users/%s/followers?page=%d";

    static String followingUrl = "https://api.github.com/users/%s/following?page=%d";

    static String userUrl = "https://api.github.com/users/%s";

    static String gitHubUsernameKey = "login";

    static String getGitHubUserIdKey = "id";

    static String getFollowersCountKey = "followers";

    static String getGitHubUserImageUrl = "avatar_url";

    static int JOB_ID = 281192;

    static String FILE_NAME = "followersList";

    static String DATABASE_NAME = "follower_tracker";

    static String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS followers(Username VARCHAR);";

    static String QUERY_INSERT_VALUE = "INSERT INTO followers('%s')";

    static String QUERY_COUNT_ROWS = "SELECT COUNT() FROM followers";

}
