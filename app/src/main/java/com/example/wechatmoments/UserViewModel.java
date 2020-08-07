package com.example.wechatmoments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wechatmoments.model.User;
import com.example.wechatmoments.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    public static final String TAG = "UserViewModel";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<User> user;
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<User> getUser() {
        if (Objects.isNull(user)) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    public void loadUserInfo() {
        userRepository.loadUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<String>(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "--------onSubscribe--------");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String str) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<User>() {}.getType();
                        User userInfo = gson.fromJson(str, type);
                        Log.d(TAG, "--------onNext--------" + userInfo.getUsername());
                        user.postValue(userInfo);

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
