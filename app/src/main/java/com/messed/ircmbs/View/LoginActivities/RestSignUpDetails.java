package com.messed.ircmbs.View.LoginActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
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

    TextInputEditText restname, restownername, restaddress, restemployees, resttables, restphone;
    MaterialCheckBox checkBox;
    MaterialButton button;
    FirebaseAuth firebaseAuth;
    private Call<SignUpCall> call;
    String s1, s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_sign_up_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //getSupportActionBar().hide();
        String fullname = getIntent().getExtras().getString("Name");
        s1 = getIntent().getStringExtra("email");
        s2 = getIntent().getStringExtra("password");
        restname = findViewById(R.id.signup_restname);
        restownername = findViewById(R.id.signup_ownername);
        restaddress = findViewById(R.id.signup_address);
        resttables = findViewById(R.id.signup_numtables);
        restemployees = findViewById(R.id.signup_numemployees);
        checkBox = findViewById(R.id.cloudornot);
        button = findViewById(R.id.nextbutton);
        restphone = findViewById(R.id.signup_phone);
        restownername.setText(fullname);
        firebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.createUserWithEmailAndPassword(s1, s2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        singleRun();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RestSignUpDetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void singleRun() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait signing you up");
        String rname = restname.getText().toString();
        String rowner = restownername.getText().toString();
        String raddress = restaddress.getText().toString();
        String rnumtables = resttables.getText().toString();
        String rnumemp = restemployees.getText().toString();
        String rphone = restphone.getText().toString();
        if (rname.isEmpty() || rowner.isEmpty() || raddress.isEmpty() || rnumtables.isEmpty() || rnumemp.isEmpty()) {
            Toast.makeText(RestSignUpDetails.this, "All fields Required", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.show();
        boolean cloudornot = checkBox.isChecked();
        String n = "0";
        if (cloudornot)
            n = "1";
        else
            n = "0";
        Log.e("TAG", "" + rname + " " + rowner + " " + raddress + " " + cloudornot);
        //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        // Log.e("TAG",""+currentFirebaseUser.getUid());
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call = networkService.signupCall(rname, firebaseAuth.getUid(), rowner, raddress, n, rnumtables, rnumemp, rphone, firebaseAuth.getCurrentUser().getEmail());
        call.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("TAG", "Sucess" + response.message());
                progressDialog.dismiss();
                startActivity(new Intent(getBaseContext(), RestaurantHomeScreen.class));
                finishAffinity();
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                Log.e("TAG", "" + t);
            }
        });
    }
}