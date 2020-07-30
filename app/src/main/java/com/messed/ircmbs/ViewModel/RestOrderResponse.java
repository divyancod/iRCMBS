package com.messed.ircmbs.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Network.RetrofitInstanceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestOrderResponse {

    public static MutableLiveData<List<MenuList>> getLiveData()
    {
        final MutableLiveData<List<MenuList>> liveData=new MutableLiveData<>();
        MenuCall menuCall= RetrofitInstanceClient.getRetrofit().create(MenuCall.class);
        Call<List<MenuList>> call=menuCall.getMenu();
        call.enqueue(new Callback<List<MenuList>>() {
            @Override
            public void onResponse(Call<List<MenuList>> call, Response<List<MenuList>> response) {
                Log.e("Runned", "onResponse: "+response.body().get(0).getItems());
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuList>> call, Throwable t) {
                Log.e("Runned", "onResponse: "+t);

            }
        });
        return liveData;
    }
}
