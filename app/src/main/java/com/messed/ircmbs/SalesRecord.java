package com.messed.ircmbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.messed.ircmbs.Model.SaleModel;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.View.Adapters.SalesRecordAdapter;
import com.messed.ircmbs.ViewModel.SalesRecordViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRecord extends AppCompatActivity {

    Toolbar toolbar;
    Call<List<SaleModel>> call;
    RecyclerView recyclerView;
    View parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_record);
        parent=findViewById(android.R.id.content);
        toolbar=findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        recyclerView=findViewById(R.id.sales_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //fetchSales();
        SalesRecordViewModel viewModel = ViewModelProviders.of(this).get(SalesRecordViewModel.class);
        final SalesRecordAdapterNew adapterNew = new SalesRecordAdapterNew(this);
        viewModel.getPagedListLiveData().observe(this, new Observer<PagedList<SalesRecordModel>>() {
            @Override
            public void onChanged(PagedList<SalesRecordModel> salesRecordModels) {
                adapterNew.submitList(salesRecordModels);
            }
        });
        recyclerView.setAdapter(adapterNew);
    }

    private void fetchSales()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait fetching sales history");
        progressDialog.show();
        UserPreference userPreference=new UserPreference(this);
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.salesList(userPreference.getResid());
        call.enqueue(new Callback<List<SaleModel>>() {
            @Override
            public void onResponse(Call<List<SaleModel>> call, Response<List<SaleModel>> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    SalesRecordAdapter adapter = new SalesRecordAdapter(getBaseContext(), response.body());
                    recyclerView.setAdapter(adapter);
                }else
                {
                    Snackbar.make(parent,"No sales record found",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SaleModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parent, "Unable to fetch data at this moment\nMay be Master Server is offline", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}