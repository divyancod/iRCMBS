package com.messed.ircmbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.messed.ircmbs.Model.SaleModel;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.View.Adapters.SalesRecordAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRecord extends AppCompatActivity {

    Toolbar toolbar;
    Call<List<SaleModel>> call;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_record);
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
        fetchSales();
    }
    private void fetchSales()
    {
        UserPreference userPreference=new UserPreference(this);
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.salesList(userPreference.getResid());
        call.enqueue(new Callback<List<SaleModel>>() {
            @Override
            public void onResponse(Call<List<SaleModel>> call, Response<List<SaleModel>> response) {
                SalesRecordAdapter adapter=new SalesRecordAdapter(getBaseContext(),response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SaleModel>> call, Throwable t) {
                Toast.makeText(SalesRecord.this, "Unable to fetch data at this moment\nMay be Master Server is offline", Toast.LENGTH_LONG).show();
            }
        });
    }
}