package com.messed.ircmbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.android.material.button.MaterialButton;
import com.messed.ircmbs.Model.AllStringHere;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.View.RestOrderConfirmation;
import com.messed.ircmbs.ViewModel.RestOrderViewModel;
import java.util.List;

public class RestOrder extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Spinner spinner;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_order);
        toolbar=findViewById(R.id.toolbar_all);
        button=findViewById(R.id.materialButton);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        UserPreference nob=new UserPreference(getBaseContext());
        progressDialog=new ProgressDialog(this);
        recyclerView=findViewById(R.id.rest_order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog.setMessage("Please wait loading menu");
        progressDialog.show();
        RestOrderViewModel restOrderViewModel=new RestOrderViewModel();
        restOrderViewModel.getListLiveData().observe(this, new Observer<List<MenuList>>() {
            @Override
            public void onChanged(List<MenuList> menuLists) {
                RestOrderAdapter restOrderAdapter=new RestOrderAdapter(getBaseContext(),menuLists);
                recyclerView.setAdapter(restOrderAdapter);
                progressDialog.dismiss();
            }
        });

        spinner=findViewById(R.id.appCompatSpinner);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, new AllStringHere().getRestOrderSpinner(Integer.parseInt(nob.getTables())));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), RestOrderConfirmation.class);
                intent.putExtra("tableno",spinner.getSelectedItemId());
                startActivity(intent);
            }
        });
    }
}
