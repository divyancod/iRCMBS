package com.messed.ircmbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.messed.ircmbs.Model.EmpDataModel;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestEmployeeList extends AppCompatActivity {

    FloatingActionButton fab;
    Call<List<EmpDataModel>> call;
    RecyclerView recyclerView;
    Toolbar toolbar;
    View parent;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_employee_list);
        fab=findViewById(R.id.employeeaddfab);
        recyclerView=findViewById(R.id.emp_recycler);
        toolbar=findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        parent=findViewById(android.R.id.content);
        progressBar=findViewById(R.id.prgbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(v -> startActivity(new Intent(getBaseContext(),RestEmployeeAdd.class)));
        //getEmpData();
    }
    private void getEmpData()
    {
        progressBar.setVisibility(View.VISIBLE);
        UserPreference userPreference=new UserPreference(getBaseContext());
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        Call<EmployeeRecordResponse> responseCall=networkService.allInOneEmployee("0",userPreference.getResid(),null,null
                                                        ,null,null,null,null,null);
        responseCall.enqueue(new Callback<EmployeeRecordResponse>() {
            @Override
            public void onResponse(Call<EmployeeRecordResponse> call, Response<EmployeeRecordResponse> response) {
                if(response.body()!=null){
                    RestEmployeeAdapter adapter = new RestEmployeeAdapter(getBaseContext(), response.body().getEmployees());
                    recyclerView.setAdapter(adapter);
                }else
                {
                    Snackbar.make(parent, "No Employee Record Found", Snackbar.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EmployeeRecordResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEmpData();
    }
}