package com.messed.ircmbs.View.LoginActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.R;
import com.messed.ircmbs.View.RestaurantHomeScreen;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class LoginChoice extends AppCompatActivity {

    MaterialButton button;
    MaterialTextView textView;
    static final String TAG="LoginChoice";
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_choice);
        textView=findViewById(R.id.not_user);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getBaseContext(),RestaurantHomeScreen.class));
            finishAffinity();
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().hide();
       getWindow().setStatusBarColor(Color.WHITE);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LoginRest.class));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),SignUpScreen.class));
                finish();
            }
        });

    }
}
