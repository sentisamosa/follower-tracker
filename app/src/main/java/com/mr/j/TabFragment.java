package com.mr.j;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    Context fragmentContext;
    TextView textView;
    LinearLayout initial_ll;
    ImageButton add_user_id;
    ListView user_list;
    SharedPreferences sharedPreferences;
    CustomAdapter customAdapter;
    int page_no = 1;

    ListView.OnScrollListener getMoreUsers = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            int threshold = 1;
            int count = user_list.getCount();

            if (scrollState == SCROLL_STATE_IDLE)
                if (user_list.getLastVisiblePosition() >= count - threshold) {
                    page_no++;
                    getUsers();
                }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    Button.OnClickListener addUserId = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(fragmentContext);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.userid_dialog);
            dialog.setTitle(Constants.dialogTitle);

            final EditText userId = dialog.findViewById(R.id.userId_EditTxt);
            Button setButton = dialog.findViewById(R.id.setButton);

            setButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editSharedPreferences(Constants.userIdKey, userId.getText().toString());
                    Toast.makeText(fragmentContext, "UserId set", Toast.LENGTH_SHORT).show();
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.detach(TabFragment.this).attach(TabFragment.this);
                    dialog.dismiss();
                }
            });

            Toast.makeText(fragmentContext, R.string.user_id_toast, Toast.LENGTH_LONG).show();
            dialog.show();
        }
    };

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
        fragmentContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.textView);
        initial_ll = view.findViewById(R.id.initial_ll);
        add_user_id = view.findViewById(R.id.add_user_id);
        user_list = view.findViewById(R.id.user_list);
        add_user_id.setOnClickListener(addUserId);
        user_list.setOnScrollListener(getMoreUsers);

        if (getSharedPreferencesValue(Constants.userIdKey).equals("")) {
            initial_ll.setVisibility(View.VISIBLE);
            user_list.setVisibility(View.INVISIBLE);
        } else {
            initial_ll.setVisibility(View.INVISIBLE);
            user_list.setVisibility(View.VISIBLE);
            getUsers();
        }
    }

    /*----methods----*/
    private String getSharedPreferencesValue(String key) {

        try {
            sharedPreferences = fragmentContext.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } catch (NullPointerException exception) {
            return "";
        }
    }

    private <T> void editSharedPreferences(String keyName, T value) {
        try {
            sharedPreferences = fragmentContext.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof String)
                editor.putString(keyName, value.toString());
            else
                editor.putBoolean(keyName, Boolean.parseBoolean(value.toString()));
            editor.apply();
        } catch (NullPointerException exception) {
            Toast.makeText(fragmentContext, "Error occurred while editing shared preferences", Toast.LENGTH_SHORT).show();
        }
    }


    /*----API Calling methods----*/
    private void getUsers() {
        GitHubAPICaller caller = new GitHubAPICaller(getContext());
        caller.getResponse(String.format(position == 0 ? Constants.followerUrl : Constants.followingUrl, getSharedPreferencesValue(Constants.userIdKey), page_no), new VolleyCallback() {
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
}
