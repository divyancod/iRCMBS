package com.messed.ircmbs.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.R;
import com.messed.ircmbs.ViewModel.RestOrderViewModel;

import java.util.List;

public class MenuViewActivity extends AppCompatActivity {

    private static final String TAG ="MenuViewActivity";
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MenuViewAdapter menuViewAdapter;

    private static String url="http://divyanshu123.000webhostapp.com/apicall.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);
        recyclerView=findViewById(R.id.menu_recycler);
        progressDialog=new ProgressDialog(this);
        toolbar=findViewById(R.id.toolbar_all);
        //setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog.setMessage("Please wait loading menu");
        progressDialog.show();
        RestOrderViewModel restOrderViewModel= ViewModelProviders.of(this).get(RestOrderViewModel.class);
        restOrderViewModel.getListLiveData().observe(this, new Observer<List<MenuList>>() {
            @Override
            public void onChanged(List<MenuList> menuLists) {
                menuViewAdapter=new MenuViewAdapter(menuLists,getBaseContext());
                progressDialog.dismiss();
                recyclerView.setAdapter(menuViewAdapter);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
