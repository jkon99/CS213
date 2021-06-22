package com.example.android29.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android29.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    public Context context;
    public List<Photo> photoList;

    private View view;

    public ImageAdapter(Context context, List<Photo> photoList){
        this.context = context;
        this.photoList = photoList;
    }
    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.photoview, null);
            ImageView imageView = view.findViewById(R.id.imageView);
            Uri uri = Uri.parse(photoList.get(position).getPath());
            imageView.setImageURI(uri);
        }else{

        }
        return view;
    }
}
