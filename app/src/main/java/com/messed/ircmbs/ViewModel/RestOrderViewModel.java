package com.messed.ircmbs.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.messed.ircmbs.Model.MenuList;

import java.util.List;

public class RestOrderViewModel extends ViewModel {

    LiveData<List<MenuList>> listLiveData;
    RestOrderResponse restOrderResponse;

    public RestOrderViewModel()
    {
        restOrderResponse=new RestOrderResponse();
    }

    public LiveData<List<MenuList>> getListLiveData()
    {
        if(listLiveData==null)
        {
            listLiveData=restOrderResponse.getLiveData();
        }
        return listLiveData;
    }

}
