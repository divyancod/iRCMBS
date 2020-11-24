package com.messed.ircmbs;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.View.*;
import com.messed.ircmbs.View.LoginActivities.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private Call<SignUpCall> userCheckCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        lottieAnimationView=findViewById(R.id.progressBar);
        lottieAnimationView.setSpeed(2.0F);
        lottieAnimationView.setProgress(0.5F);
        lottieAnimationView.setRepeatMode(LottieDrawable.RESTART);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            userCheckCal();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView.cancelAnimation();
                    startActivity(new Intent(getBaseContext(),LoginChoice.class));
                    finish();
                }
            },3000);
        }
    }

    private void userCheckCal() {
        String resid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        userCheckCall = networkService.userCheck(resid);
        userCheckCall.enqueue(new Callback<SignUpCall>() {
            @Override
            public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                Log.e("tag", "" + response.body().getStatus());
                UserPreference nob = new UserPreference(MainActivity.this);
                if (response.body().getError().equals("1")) {
                    nob.setAccountStatus(MainActivity.this, response.body().getError());
//                    Snackbar.make(findViewById(android.R.id.content), "Account not verified contact developer at divyanfun@gmail.com", Snackbar.LENGTH_INDEFINITE).show();
                } else {
                    nob.setAccountStatus(MainActivity.this, response.body().getError());
                }
                lottieAnimationView.cancelAnimation();
                startActivity(new Intent(MainActivity.this,RestaurantHomeScreen.class));
                finish();
            }

            @Override
            public void onFailure(Call<SignUpCall> call, Throwable t) {
                Log.e("tag", "" + t);
            }
        });
    }
}
