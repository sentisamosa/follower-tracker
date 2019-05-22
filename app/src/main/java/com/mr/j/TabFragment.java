package com.mr.j;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    int position;
    TextView textView;
    LinearLayout initial_ll;
    Button add_user_id;
    ListView user_list;
    SharedPreferences sharedPreferences;
    CustomAdapter customAdapter;
    int page_no = 1;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Todo check if we have a user id saved in shared preferences. if yes - fade the linear layout, else fade the list view
        //Todo initialize the ui components and make relevant api calls

        textView = view.findViewById(R.id.textView);
        initial_ll = view.findViewById(R.id.initial_ll);
        add_user_id = view.findViewById(R.id.add_user_id);

        if (getSharedPreferencesValue(Constants.userIdKey).equals("")) {
            initial_ll.setVisibility(View.VISIBLE);
            user_list.setVisibility(View.INVISIBLE);
        } else {
            initial_ll.setVisibility(View.VISIBLE);
            user_list.setVisibility(View.GONE);

        }
    }

    /*----methods----*/
    private String getSharedPreferencesValue(String key) {

        try {
            sharedPreferences = getContext().getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } catch (NullPointerException exception) {
            return "";
        }
    }

    private <T> void editSharedPreferences(String keyName, T value) {
        try {
            sharedPreferences = getContext().getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof String)
                editor.putString(keyName, value.toString());
            else
                editor.putBoolean(keyName, Boolean.parseBoolean(value.toString()));
            editor.apply();
        } catch (NullPointerException exception) {
            Toast.makeText(getContext(), "Error occurred while editing shared preferences", Toast.LENGTH_SHORT).show();
        }
    }


    /*----API Calling methods----*/
    private void getUsers() {
        GitHubAPICaller caller = new GitHubAPICaller(getContext());
        caller.getResponse(String.format(position == 0? Constants.followerUrl : Constants.followingUrl, Constants.userIdValue, page_no), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {
                populateListView(result);
            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImageResponse(Bitmap image) {

            }
        });
    }

    private void populateListView(JSONArray jsonArray) {
        try {
            ArrayList<UserItem> users = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tempObj = jsonArray.getJSONObject(i);
                users.add(new UserItem(tempObj.getString(Constants.gitHubUsernameKey), tempObj.getString(Constants.getGitHubUserIdKey), tempObj.getString(Constants.getGitHubUserImageUrl)));
            }

            if (customAdapter == null) {
                customAdapter = new CustomAdapter(getContext(), R.layout.user_list, users);
                user_list.setAdapter(customAdapter);
            } else {
                customAdapter.addAll(users);
                customAdapter.notifyDataSetChanged();
            }
        } catch (JSONException exception) {
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
