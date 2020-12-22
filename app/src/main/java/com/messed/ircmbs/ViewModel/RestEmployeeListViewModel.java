package com.messed.ircmbs.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.messed.ircmbs.Employee;

import java.util.List;

public class RestEmployeeListViewModel extends ViewModel {

    LiveData<List<Employee>> liveData;
    RestEmployeeListFactory restEmployeeListFactory;
    public RestEmployeeListViewModel()
    {
        restEmployeeListFactory=new RestEmployeeListFactory();
    }
    public LiveData<List<Employee>> getEmployeeData(Context context)
    {
        if(liveData==null)
        {
            liveData=restEmployeeListFactory.getEmployees(context);
        }
        return liveData;
    }
    public LiveData<List<Employee>> getNewEmployee(Context context)
    {
        liveData=restEmployeeListFactory.getEmployees(context);
        return liveData;
    }
    
}
