package com.mr.j;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class GitHubAPICaller {

    private Context context;

    GitHubAPICaller(Context context) {
        this.context = context;
    }

    void getResponse(String url, final VolleyCallback callback) {

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

    int getFollowerCount(String userId) throws IOException, JSONException {
        URL object = new URL(String.format(Constants.USER_URL, userId));
        HttpURLConnection connection = (HttpURLConnection) object.openConnection();

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }

        input.close();
        connection.disconnect();

        return Integer.parseInt((new JSONObject(response.toString())).get("followers").toString());
    }
}
