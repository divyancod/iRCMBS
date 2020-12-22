package com.messed.ircmbs.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.SalesRecordModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRecordDataSource extends PageKeyedDataSource<Long, SalesRecordModel> {

    NetworkService networkService;
    Map<String,String> map = new HashMap<>();
    int page=0;
    public SalesRecordDataSource()
    {
        this.map.put("page","0");
        this.map.put("resid","2693");

    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, SalesRecordModel> callback) {
        this.map.put("page",String.valueOf(++page));
        networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<List<SalesRecordModel>> call = networkService.getSales(this.map);
        call.enqueue(new Callback<List<SalesRecordModel>>() {
            @Override
            public void onResponse(Call<List<SalesRecordModel>> call, Response<List<SalesRecordModel>> response) {
                Log.e("TAG","DATA SOURCE");
                List<SalesRecordModel> salesRecordModel = response.body();
                callback.onResult(salesRecordModel,null,(long)page+1);
            }

            @Override
            public void onFailure(Call<List<SalesRecordModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, SalesRecordModel> callback) {
        map.replace("page",String.valueOf(++page));
        networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<List<SalesRecordModel>> call=networkService.getSales(this.map);
        call.enqueue(new Callback<List<SalesRecordModel>>() {
            @Override
            public void onResponse(Call<List<SalesRecordModel>> call, Response<List<SalesRecordModel>> response) {
                Long key=(params.key > 1) ? params.key-1 : null;
                List<SalesRecordModel> salesRecordModel = response.body();
                callback.onResult(salesRecordModel,key);
            }

            @Override
            public void onFailure(Call<List<SalesRecordModel>> call, Throwable t) {
                Log.e("TAG","Failed load after"+t);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, SalesRecordModel> callback) {
        map.replace("page",String.valueOf(++page));
        networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<List<SalesRecordModel>> call=networkService.getSales(this.map);
        call.enqueue(new Callback<List<SalesRecordModel>>() {
            @Override
            public void onResponse(Call<List<SalesRecordModel>> call, Response<List<SalesRecordModel>> response) {
                List<SalesRecordModel> salesRecordModel = response.body();
                callback.onResult(salesRecordModel,(long)page+1);
            }

            @Override
            public void onFailure(Call<List<SalesRecordModel>> call, Throwable t) {
                Log.e("TAG","Failed load after"+t);
            }
        });
    }
}
