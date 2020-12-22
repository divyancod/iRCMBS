package com.messed.ircmbs.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.messed.ircmbs.Employee;
import com.messed.ircmbs.EmployeeRecordResponse;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestEmployeeListFactory {

    public static MutableLiveData<List<Employee>> getEmployees(Context context)
    {
       final MutableLiveData<List<Employee>> liveData=new MutableLiveData<>();
        UserPreference userPreference=new UserPreference(context);
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<EmployeeRecordResponse> call=networkService.allInOneEmployee("0",userPreference.getResid(),null,null
                ,null,null,null,null,null);
        call.enqueue(new Callback<EmployeeRecordResponse>() {
            @Override
            public void onResponse(Call<EmployeeRecordResponse> call, Response<EmployeeRecordResponse> response) {
                liveData.setValue(response.body().getEmployees());
            }

            @Override
            public void onFailure(Call<EmployeeRecordResponse> call, Throwable t) {
                Log.e("tag","Something went wrong in rest employee fetch");
            }
        });

        return liveData;
    }
}
