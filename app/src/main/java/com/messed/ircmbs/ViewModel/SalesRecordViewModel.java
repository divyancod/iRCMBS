package com.messed.ircmbs.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.messed.ircmbs.Model.SalesRecordModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SalesRecordViewModel extends AndroidViewModel {
    MutableLiveData<SalesRecordDataSource> dataSourceMutableLiveData;
    SalesRecordFactory salesRecordFactory;
    Executor executors;
    LiveData<PagedList<SalesRecordModel>> pagedListLiveData;


    public SalesRecordViewModel(@NonNull Application application) {
        super(application);
        salesRecordFactory = new SalesRecordFactory();
        dataSourceMutableLiveData = salesRecordFactory.getLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setPrefetchDistance(4)
                .build();
        executors = Executors.newFixedThreadPool(5);
        pagedListLiveData = (new LivePagedListBuilder<Integer,SalesRecordModel>(salesRecordFactory,config))
                .setFetchExecutor(executors)
                .build();
    }
    public LiveData<PagedList<SalesRecordModel>> getPagedListLiveData()
    {
        return pagedListLiveData;
    }
}
