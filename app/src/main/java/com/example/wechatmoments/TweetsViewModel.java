package com.example.wechatmoments;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
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
import java.util.stream.Collectors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TweetsViewModel extends ViewModel {

    public static final String TAG = "TweetsViewModel";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Tweet>> tweets;
    private TweetRepository tweetRepository;

    public void setTweetRepository(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    private LiveData<List<Tweet>> getTweets() {
        if (Objects.isNull(tweets)) {
            tweets = new MutableLiveData<List<Tweet>>();
        }
        return tweets;
    }

    public void observeTweets(LifecycleOwner lifecycleOwner, androidx.lifecycle.Observer<List<Tweet>> observer) {
        this.getTweets().observe(lifecycleOwner, observer);
    }

    public void loadTweets() {
        tweetRepository.loadTweets()
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<Tweet>>(){
                    public List<Tweet> apply(String str) throws Exception {
                        Type collectionType = new TypeToken<List<Tweet>>(){}.getType();
                        return new Gson().fromJson(str, collectionType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<Tweet>>(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "--------onSubscribe--------");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Tweet> list) {
                        Log.d(TAG, "--------onNext--------");
                        list = list.stream().filter((Tweet tweet) ->
                                (Objects.nonNull(tweet.getContent()) || Objects.nonNull(tweet.getImages()))
                        ).collect(Collectors.toList());
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
