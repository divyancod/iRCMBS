package com.messed.ircmbs.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.messed.ircmbs.Model.SaleModel;
import com.messed.ircmbs.R;
import com.messed.ircmbs.ViewModel.SalesRecordViewModel;

import java.util.List;

import retrofit2.Call;

public class SalesRecord extends AppCompatActivity {

    Toolbar toolbar;
    Call<List<SaleModel>> call;
    RecyclerView recyclerView;
    View parent;
    TextView toolbartitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_record);
        parent=findViewById(android.R.id.content);
        toolbar=findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbartitle=toolbar.findViewById(R.id.toolbar_title);
        toolbartitle.setText("Sales Record");
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });
        recyclerView=findViewById(R.id.sales_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //fetchSales();
        SalesRecordViewModel viewModel = ViewModelProviders.of(this).get(SalesRecordViewModel.class);
        final SalesRecordAdapterNew adapterNew = new SalesRecordAdapterNew(this);
        viewModel.getPagedListLiveData().observe(this, salesRecordModels -> adapterNew.submitList(salesRecordModels));
        recyclerView.setAdapter(adapterNew);
    }

}