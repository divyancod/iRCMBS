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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_employee_list);
        fab=findViewById(R.id.employeeaddfab);
        recyclerView=findViewById(R.id.emp_recycler);
        toolbar=findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        parent=findViewById(android.R.id.content);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),RestEmployeeAdd.class));
            }
        });
        getEmpData();
    }
    private void getEmpData()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait fetching employee list");
        progressDialog.show();
        UserPreference userPreference=new UserPreference(getBaseContext());
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.getEmployee(userPreference.getResid());
        call.enqueue(new Callback<List<EmpDataModel>>() {
            @Override
            public void onResponse(Call<List<EmpDataModel>> call, Response<List<EmpDataModel>> response) {
                //Log.e("TAG",""+response.body().get(0).getEmpname());
                if(response.body()!=null){
                    RestEmployeeAdapter adapter = new RestEmployeeAdapter(getBaseContext(), response.body());
                    recyclerView.setAdapter(adapter);
                }else
                {
                    Snackbar.make(parent, "No Employee Record Found", Snackbar.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<EmpDataModel>> call, Throwable t) {
                Log.e("RestEmpLIST",""+t);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEmpData();
    }
}