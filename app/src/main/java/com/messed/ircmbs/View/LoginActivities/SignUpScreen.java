package com.messed.ircmbs.View.LoginActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.R;
import com.messed.ircmbs.View.RestaurantHomeScreen;

public class SignUpScreen extends AppCompatActivity {

    String usertype[]={"Restaurant Representative","Employee of Restaurant"};
    AppCompatSpinner spinner;
    TextInputEditText editText1,editText2;
    FirebaseAuth firebaseAuth;
    MaterialButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        spinner=findViewById(R.id.signup_spinner);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,usertype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        editText1=findViewById(R.id.signup_email);
        editText2=findViewById(R.id.signup_password);
        button=findViewById(R.id.materialButton3);
        firebaseAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2;
                s1=editText1.getText().toString().trim();
                s2=editText2.getText().toString().trim();
                if(!s1.isEmpty() && !s2.isEmpty())
                {
                    firebaseAuth.createUserWithEmailAndPassword(s1,s2);
                    startActivity(new Intent(getBaseContext(),RestaurantHomeScreen.class));
                    finishAffinity();
                }
            }
        });

    }
}
