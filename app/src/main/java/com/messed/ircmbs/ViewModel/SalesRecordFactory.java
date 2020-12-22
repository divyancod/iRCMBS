package com.messed.ircmbs.ViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class SalesRecordFactory extends DataSource.Factory{
    SalesRecordDataSource dataSource;
    MutableLiveData<SalesRecordDataSource> liveData;
    public SalesRecordFactory()
    {
        liveData=new MutableLiveData<>();
    }
    @NonNull
    @Override
    public DataSource create() {
        dataSource = new SalesRecordDataSource();
        liveData.postValue(dataSource);
        return dataSource;
    }
    public MutableLiveData<SalesRecordDataSource> getLiveData()
    {
        return liveData;
    }
}
