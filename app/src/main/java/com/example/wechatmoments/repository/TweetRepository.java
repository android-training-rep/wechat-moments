package com.example.wechatmoments.repository;

import com.example.wechatmoments.utils.HttpUtil;
import com.example.wechatmoments.utils.UrlUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TweetRepository {
    private HttpUtil httpUtil;

    public TweetRepository(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    public Observable loadTweets() {
        return Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                httpUtil.request(UrlUtil.TWEETS_URL, emitter);
            }
        });
    }
}
