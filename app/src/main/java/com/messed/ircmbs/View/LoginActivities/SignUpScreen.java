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

/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class SignUpScreen extends AppCompatActivity {

    String usertype[]={"Restaurant Representative","Employee of Restaurant"};
    AppCompatSpinner spinner;
    TextInputEditText editText1,editText2,restname;
    FirebaseAuth firebaseAuth;
    MaterialButton button;
    MaterialTextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //getSupportActionBar().hide();
        spinner=findViewById(R.id.signup_spinner);
        textView=findViewById(R.id.alreadyuser);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,usertype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        editText1=findViewById(R.id.signup_email);
        editText2=findViewById(R.id.signup_password);
        button=findViewById(R.id.materialButton3);
        restname=findViewById(R.id.signup_fullname);
        firebaseAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2;
                s1=editText1.getText().toString().trim();
                s2=editText2.getText().toString().trim();
                if(!s1.isEmpty() && !s2.isEmpty() &&s1.length()>8 && s2.length()>6)
                {
                    firebaseAuth.createUserWithEmailAndPassword(s1,s2);
                    Intent intent=new Intent(getBaseContext(), RestSignUpDetails.class);
                    intent.putExtra("Name",restname.getText().toString());
                    startActivity(intent);
                    //finishAffinity();
                }else
                {
                    Toast.makeText(SignUpScreen.this,"Length should be above 8 for email and 6 for password",Toast.LENGTH_LONG).show();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
