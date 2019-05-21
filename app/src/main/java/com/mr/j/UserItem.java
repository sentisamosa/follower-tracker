package com.mr.j;

import android.graphics.Bitmap;

class UserItem {

    private String username;
    private String userid;
    private String userImageUrl;

    UserItem(String username, String userid, String userImageUrl){
        this.username = username;
        this.userid = userid;
        this.userImageUrl = userImageUrl;
    }

    String getUsername() {
        return username;
    }

    String getUserid(){
        return userid;
    }

    String getUserImageUrl(){return userImageUrl;}
}
