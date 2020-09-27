package com.messed.ircmbs.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
* Created By MrMessedUp(Divyanshu Verma)
* */

public class RetrofitInstanceClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        String url="http://divyanshu123.000webhostapp.com/";
        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
