package com.example.wechatmoments.myadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wechatmoments.R;

import java.util.Objects;

public class ImagesAdapter extends BaseAdapter {
    String[] images;
    public ImagesAdapter(String[] images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        if (Objects.nonNull(images)) {
            return images.length;
        } else {
            return 0;
        }
    }

    @Override
    public String getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View grid;
        if (view == null) {
            grid = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, null);
            if (Objects.nonNull(images[position])) {
                ImageView imageView = grid.findViewById(R.id.image);
                Glide.with(imageView).load(images[position]).into(imageView);
            }
        } else {
            grid = (View) view;
        }
        return grid;
    }
}
