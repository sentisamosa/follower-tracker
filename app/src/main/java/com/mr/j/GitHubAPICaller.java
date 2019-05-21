package com.mr.j;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

class GitHubAPICaller {

    private RequestQueue requestQueue;
    JsonArrayRequest arrayRequest;
    private Context context;

    GitHubAPICaller(Context context) {
        this.context = context;
    }

    void getResponse(String url, final VolleyCallback callback) {

        requestQueue = Singleton.getInstance(context).getRequestQueue();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onErrorResponse(error.getMessage());
            }
        });

        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    void getImage(String url, final VolleyCallback callback) {
        ImageLoader imageLoader = Singleton.getInstance(context).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                callback.onImageResponse(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onErrorResponse(error.getMessage());
            }
        });
    }
}
