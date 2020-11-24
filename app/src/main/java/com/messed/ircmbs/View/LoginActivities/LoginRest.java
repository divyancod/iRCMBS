package com.messed.ircmbs.View.LoginActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.R;
import com.messed.ircmbs.View.RestaurantHomeScreen;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class LoginRest extends AppCompatActivity {

    MaterialButton button;
    TextInputEditText e1,e2;
    String s1,s2;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_rest);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //getSupportActionBar().hide();
        e1=findViewById(R.id.rest_login_email);
        e2=findViewById(R.id.rest_login_password);
        button=findViewById(R.id.restloginbutton);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Logging in");
                progressDialog.show();
                s1=e1.getText().toString().trim();
                s2=e2.getText().toString().trim();
                if(!s1.isEmpty()&& !s2.isEmpty())
                {
                    firebaseAuth.signInWithEmailAndPassword(s1,s2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(getBaseContext(),RestaurantHomeScreen.class));
                                finishAffinity();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(LoginRest.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
