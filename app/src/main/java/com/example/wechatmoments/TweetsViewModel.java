package com.example.wechatmoments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wechatmoments.model.Tweet;
import com.example.wechatmoments.repository.TweetRepository;

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
                    public void onNext(String s) {
                        Log.d(TAG, "--------onNext--------" + s);
                        // todo 处理数据
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
}
