package com.example.wechatmoments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wechatmoments.model.Comment;

import java.util.Objects;

public class CommentsAdapter extends BaseAdapter {
    Comment[] comments;
    public CommentsAdapter(Comment[] comments) {
        this.comments = comments;
    }

    @Override
    public int getCount() {
        if (Objects.nonNull(comments)) {
            return comments.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View listView;
        if (view == null) {
            listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item, null);
            if (Objects.nonNull(comments[position])) {
                TextView nickView = listView.findViewById(R.id.nick);
                TextView contentView = listView.findViewById(R.id.content);
                nickView.setText(comments[position].getSender().getNick());
                contentView.setText(" : " + comments[position].getContent());
            }
        } else {
            listView = (View) view;
        }
        return listView;
    }
}
