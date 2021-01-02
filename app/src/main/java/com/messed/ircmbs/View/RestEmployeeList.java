package com.messed.ircmbs.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.messed.ircmbs.Model.EmpDataModel;
import com.messed.ircmbs.Model.EmployeeRecordResponse;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.View.RestEmployeeAdapter;
import com.messed.ircmbs.ViewModel.RestEmployeeListViewModel;
import com.messed.ircmbs.ui.RestEmployeeAdd;

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
    RestEmployeeListViewModel viewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    RestEmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_employee_list);
        fab = findViewById(R.id.employeeaddfab);
        recyclerView = findViewById(R.id.emp_recycler);
        toolbar = findViewById(R.id.toolbar_all);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        parent = findViewById(android.R.id.content);
        progressBar = findViewById(R.id.prgbar);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), RestEmployeeAdd.class)));

        viewModel = new ViewModelProvider(this).get(RestEmployeeListViewModel.class);
        viewModel.getEmployeeData(getApplicationContext()).observe(this, employees -> {
            adapter = new RestEmployeeAdapter(getBaseContext(), employees);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void getAll() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}