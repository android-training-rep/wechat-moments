package com.example.wechatmoments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wechatmoments.model.Tweet;
import com.example.wechatmoments.repository.TweetRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TweetsViewModel extends ViewModel {

    public static final String TAG = "TweetsViewModel";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Tweet>> tweets;
    private TweetRepository tweetRepository;

    public void setTweetRepository(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public LiveData<List<Tweet>> getTweets() {
        if (Objects.isNull(tweets)) {
            tweets = new MutableLiveData<List<Tweet>>();
        }
        return tweets;
    }

    public void loadTweets() {
        tweetRepository.loadTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<String>(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "--------onSubscribe--------");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String str) {
                        Log.d(TAG, "--------onNext--------");
                        // todo 处理数据
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<List<Tweet>>(){}.getType();
                        List<Tweet> list = gson.fromJson(str, collectionType);
                        tweets.postValue(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "--------onError--------");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "--------onComplete--------");
                    }
                });
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
