package com.carl.mvpdemo.pub.network;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Carl
 * version 1.0
 * @since 2018/6/10
 */
public class HttpClient {

    private static final HttpClient INSTANCE = new HttpClient();
    private Map<String, Object> mServiceMap = new HashMap<>();

    public static HttpClient getInstance() {
        return HttpClient.INSTANCE;
    }

    private HttpClient() {

    }


    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = "";
        }
        Object serviceObj = mServiceMap.get(serviceClass.getName() + baseUrl);
        if (serviceObj != null) {
            return (T) serviceObj;
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            T service = retrofit.create(serviceClass);
            mServiceMap.put(serviceClass.getName() + baseUrl, service);
            return retrofit.create(serviceClass);
        }

    }

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .sslSocketFactory(SSLSocketClient.getSslFactory())
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)//超过时间
            .build();
}
