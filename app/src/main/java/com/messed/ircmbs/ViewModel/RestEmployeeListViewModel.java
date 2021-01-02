package com.messed.ircmbs.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.messed.ircmbs.Model.Employee;

import java.util.List;

public class RestEmployeeListViewModel extends ViewModel {

    public MutableLiveData<List<Employee>> liveData;
    RestEmployeeListFactory restEmployeeListFactory;
    public RestEmployeeListViewModel()
    {
        restEmployeeListFactory=new RestEmployeeListFactory();
    }
    public MutableLiveData<List<Employee>> getEmployeeData(Context context)
    {
        if(liveData==null)
        {
            liveData=restEmployeeListFactory.getEmployees(context);
        }
        return liveData;
    }
}
