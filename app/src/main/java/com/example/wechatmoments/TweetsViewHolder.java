package com.example.wechatmoments;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TweetsViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public ImageView avatarView;
    public TextView senderNickView, contentView;
    public GridView imagesGridView;
    public ListView commentsView;

    public TweetsViewHolder(View v){
        super(v);
        itemView = v;
        avatarView = itemView.findViewById(R.id.avatar);
        senderNickView = itemView.findViewById(R.id.sender_nick);
        contentView = itemView.findViewById(R.id.content);
        imagesGridView = itemView.findViewById(R.id.images);
        commentsView = itemView.findViewById(R.id.comments);
    }
}
