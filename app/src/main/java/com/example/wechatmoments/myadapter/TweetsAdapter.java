package com.example.wechatmoments.myadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wechatmoments.R;
import com.example.wechatmoments.TweetsViewHolder;
import com.example.wechatmoments.model.Comment;
import com.example.wechatmoments.model.Tweet;
import com.example.wechatmoments.model.User;

import java.util.List;
import java.util.Objects;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsViewHolder> {
    private List<Tweet> tweets;
    Context context;

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TweetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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
            holder.contentView.setText(currentTweet.getContent());
        } else {
            holder.contentView.setVisibility(View.GONE);
        }

        if (Objects.nonNull(sender)) {
            holder.senderNickView.setText(sender.getNick());
            ImageView avatarView = holder.avatarView;
            Glide.with(avatarView).load(currentTweet.getSender().getAvatar()).into(avatarView);
        }

        if (Objects.nonNull(images)) {
            ImagesAdapter imagesAdapter = new ImagesAdapter(images);
            holder.imagesGridView.setAdapter(imagesAdapter);
        }

        if (Objects.nonNull(comments)) {
            holder.commentsView.setVisibility(View.VISIBLE);
            CommentsAdapter commentsAdapter = new CommentsAdapter(comments);
            holder.commentsView.setAdapter(commentsAdapter);
        } else {
            holder.commentsView.setVisibility(View.GONE);
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
