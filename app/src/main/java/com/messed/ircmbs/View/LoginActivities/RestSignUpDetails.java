package com.messed.ircmbs.View.LoginActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.View.RestaurantHomeScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestSignUpDetails extends AppCompatActivity {

    TextInputEditText restname,restownername,restaddress,restemployees,resttables;
    MaterialCheckBox checkBox;
    MaterialButton button;
    FirebaseAuth firebaseAuth;
    private Call<SignUpCall> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_sign_up_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
        String fullname=getIntent().getExtras().getString("Name");
        restname=findViewById(R.id.signup_restname);
        restownername=findViewById(R.id.signup_ownername);
        restaddress=findViewById(R.id.signup_address);
        resttables=findViewById(R.id.signup_numtables);
        restemployees=findViewById(R.id.signup_numemployees);
        checkBox=findViewById(R.id.cloudornot);
        button=findViewById(R.id.nextbutton);
        restownername.setText(fullname);
        firebaseAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleRun();
                startActivity(new Intent(getBaseContext(), RestaurantHomeScreen.class));
                finishAffinity();
            }
        });
    }

    private void singleRun()
    {
        String rname=restname.getText().toString();
        String rowner=restownername.getText().toString();
        String raddress=restaddress.getText().toString();
        String rnumtables=resttables.getText().toString();
        String rnumemp=restemployees.getText().toString();
        boolean cloudornot = checkBox.isChecked();
        String n="0";
        if(cloudornot)
            n="1";
        else
            n="0";
        Log.e("TAG",""+rname+" "+rowner+" "+raddress+" "+cloudornot);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Log.e("TAG",""+currentFirebaseUser.getUid());
        NetworkService networkService= RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call=networkService.signupCall(rname,firebaseAuth.getUid(),rowner,raddress,n,rnumtables,rnumemp);
        call.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("TAG","Sucess"+response.message());
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("TAG",""+t);
            }
        });
    }
}