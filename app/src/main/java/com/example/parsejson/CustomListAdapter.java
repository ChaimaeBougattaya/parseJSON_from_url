package com.example.parsejson;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Picture> {
    ArrayList<Picture> ListData;
    Context context;
    int resource;
    public CustomListAdapter(Context context, int resource,ArrayList<Picture> list) {
        super(context, resource, list);
        this.context = context;
        this.resource=resource;
        this.ListData = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_layout,null,true);
        }
        Picture picture = getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);//image
        Picasso.with(context).load(picture.getUrlPic()).into(imageView);
        TextView textName = (TextView) convertView.findViewById(R.id.txtTitle);//title
        textName.setText(picture.getTitle());
        TextView textArtist = (TextView) convertView.findViewById(R.id.txtAuthor);//author
        textArtist.setText("By : @"+picture.getAuthor());
        return convertView;
    }
}
