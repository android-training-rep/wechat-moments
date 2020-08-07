package com.example.wechatmoments.utils;

import java.io.IOException;

import io.reactivex.ObservableEmitter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public void request(String url, ObservableEmitter emitter) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            emitter.onNext(response.body().string());
            emitter.onComplete();
        } else if (!response.isSuccessful()) {
            emitter.onError(new Exception("error"));
        }
    }
}
