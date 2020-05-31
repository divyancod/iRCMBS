package com.messed.ircmbs.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.messed.ircmbs.R;

public class RestBilling extends AppCompatActivity {

    AppCompatSpinner spinner;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_billing);
        spinner=findViewById(R.id.rest_billing_spinner);
        spinner.setPrompt("Title");
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
    }
}
