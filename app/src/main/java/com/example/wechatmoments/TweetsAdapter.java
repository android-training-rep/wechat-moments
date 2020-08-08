package com.example.wechatmoments;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wechatmoments.model.Comment;
import com.example.wechatmoments.model.Tweet;
import com.example.wechatmoments.model.User;

import java.util.List;
import java.util.Objects;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsViewHolder> {
    private List<Tweet> tweets;

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TweetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
        return new TweetsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetsViewHolder holder, int position) {
        Tweet currentTweet = tweets.get(position);
        String content = currentTweet.getContent();
        User sender = currentTweet.getSender();
        String[] images = currentTweet.getImages();
        Comment[] comments = currentTweet.getComments();
        if (Objects.nonNull(content)) {
            System.out.println("---------have content---------" + position);
            holder.contentView.setText(currentTweet.getContent());
        }
        if (Objects.nonNull(sender)) {
            System.out.println("---------have sender---------" + position);
            holder.senderNickView.setText(sender.getNick());
            ImageView avatarView = holder.avatarView;
            Glide.with(avatarView).load(currentTweet.getSender().getAvatar()).into(avatarView);
        }
        if (Objects.nonNull(images)) {
            System.out.println("---------have images---------" + position);
        }
        if (Objects.nonNull(comments)) {
            System.out.println("---------have comments---------" + position);

        }
    }

    @Override
    public int getItemCount() {
        if(Objects.nonNull(tweets)) {
            return tweets.size();
        }
        return 0;
    }
}
