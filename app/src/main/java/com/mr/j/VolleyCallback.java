package com.mr.j;

import android.graphics.Bitmap;

import org.json.JSONArray;

public interface VolleyCallback {
    void onSuccessResponse(JSONArray result);

    void onErrorResponse(String error);

    void onImageResponse(Bitmap image);
}
