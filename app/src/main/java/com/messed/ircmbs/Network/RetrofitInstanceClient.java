package com.messed.ircmbs.Network;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.messed.ircmbs.BuildConfig;

import org.jetbrains.annotations.NotNull;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
/*
* Created By MrMessedUp(Divyanshu Verma)
* */

public class RetrofitInstanceClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(getInterceptor()).build();
        String url="http://divyanshu123.000webhostapp.com/";
        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(url).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String message) {
                int maxLogSize = 1000;
                for (int i = 0; i <= message.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > message.length() ? message.length() : end;
                    Log.v("iRCMBS Resturant Log ", message.substring(start, end));
                }

            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
