package com.mr.j;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

public class CustomAdapter extends ArrayAdapter<UserItem> {

    private ArrayList<UserItem> userItems;
    private Context context;

    CustomAdapter(Context context, int textViewResourceId, ArrayList<UserItem> objects){
        super(context, textViewResourceId, objects);
        userItems = objects;
        this.context = context;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_list, null);

        TextView username = (TextView)view.findViewById(R.id.user_name);
        TextView userid = (TextView)view.findViewById(R.id.user_id);
        ImageView userImage = (ImageView)view.findViewById(R.id.user_image);
        username.setText(userItems.get(position).getUsername());
        userid.setText(userItems.get(position).getUserid());
        setImageBitmap(userItems.get(position).getUserImageUrl(), userImage);

        return view;
    }

    void addAll(ArrayList<UserItem> users){
        userItems.addAll(users);
    }

    private void setImageBitmap(String url, final ImageView imageView){
        GitHubAPICaller gitHubAPICaller = new GitHubAPICaller(context);

        gitHubAPICaller.getImage(url, new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONArray result) {

            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(context,"Error getting image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImageResponse(Bitmap image) {
              imageView.setImageBitmap(image);
            }
        });
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
    }
}
