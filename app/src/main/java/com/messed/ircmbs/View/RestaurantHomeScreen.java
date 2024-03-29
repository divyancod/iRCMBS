package com.messed.ircmbs.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.messed.ircmbs.ui.AccountSettings;
import com.messed.ircmbs.ui.MiscNoteActivity;
import com.messed.ircmbs.Model.MenuDataBase;
import com.messed.ircmbs.Model.MenuList;
import com.messed.ircmbs.Model.RestLoggedData;
import com.messed.ircmbs.Model.SignUpCall;
import com.messed.ircmbs.Network.NetworkService;
import com.messed.ircmbs.Network.RetrofitInstanceClient;
import com.messed.ircmbs.R;
import com.messed.ircmbs.Model.UserPreference;
import com.messed.ircmbs.RestAccountGST;
import com.messed.ircmbs.View.LoginActivities.LoginChoice;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class RestaurantHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;
    private Call<RestLoggedData> call;
    private Call<List<MenuList>> menuCall;
    private Call<SignUpCall> userCheckCall;
    TextView navdrawer_title;
    static final String TAG = "RestHome";
    private UserPreference nob;
    CircleImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_home_screen);
        toolbar = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.drawer_home);
        navigationView = findViewById(R.id.navbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        firebaseAuth = FirebaseAuth.getInstance();
        View headerview = navigationView.getHeaderView(0);
        navdrawer_title = headerview.findViewById(R.id.nav_drawer_textfield);
        profilepic = headerview.findViewById(R.id.profile_image);
        Log.e("TAG", "" + profilepic);
        String test = "http://divyanshu123.000webhostapp.com/frontend/pic001.jpg";
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_draw_open, R.string.nav_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.restframelayout, new FragHomeScreen()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        nob = new UserPreference(getBaseContext());
        if (UserPreference.getInit(getBaseContext()) == 0) {
            getRestData();
            fetchMenu();
            userCheckCal(true);
        } else {
            userCheckCal(false);
            navdrawer_title.setText(nob.getRestname());
            Glide.with(getApplicationContext()).load(nob.getProfilePic()).placeholder(R.drawable.templogo).error(R.drawable.templogo).into(profilepic);
        }
    }

    private void getRestData() {
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        call = networkService.restDataCall(firebaseAuth.getUid());
        call.enqueue(new Callback<RestLoggedData>() {
            @Override
            public void onResponse(Call<RestLoggedData> call, Response<RestLoggedData> response) {
                Log.e("TAG", "Getting Rest Data From Server");
                nob.setData(getBaseContext(), response.body());
                navdrawer_title.setText(response.body().getRestname());
                Glide.with(getApplicationContext()).load(response.body().getProfilepic()).placeholder(R.drawable.templogo).error(R.drawable.templogo).into(profilepic);
            }

            @Override
            public void onFailure(Call<RestLoggedData> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.restframelayout, new FragHomeScreen()).commit();
                break;
            case R.id.nav_myacc:
                startActivity(new Intent(RestaurantHomeScreen.this, AccountSettings.class));
                break;
            case R.id.nav_logout:
                signOut();
                break;
            case R.id.nav_earning:
                startActivity(new Intent(RestaurantHomeScreen.this, RestAccountGST.class));
                break;
            case R.id.nav_note:
                startActivity(new Intent(RestaurantHomeScreen.this, MiscNoteActivity.class));
                break;
            case R.id.contactus:
                String url = "http://divyanshu123.000webhostapp.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.logout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchMenu() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait fetching the default menu from server");
        progressDialog.show();
        NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
        menuCall = networkService.getAllMenu();
        menuCall.enqueue(new Callback<List<MenuList>>() {
            @Override
            public void onResponse(Call<List<MenuList>> call, Response<List<MenuList>> response) {
                MenuDataBase nob = new MenuDataBase(getBaseContext());
                nob.addMenu(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<MenuList>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void printMenu() {
        MenuDataBase nob = new MenuDataBase(getBaseContext());
        List<MenuList> menu = nob.getAllItems();
        for (int i = 0; i < menu.size(); i++) {
            Log.e("TAG", "=====" + menu.get(i).getItems() + "=" + menu.get(i).getPrice() + "=" + menu.get(i).getRating());
        }
    }

    private void signOut() {
        firebaseAuth.signOut();
        nob.delete();
        MenuDataBase menuDataBase = new MenuDataBase(this);
        menuDataBase.deleteTable();
        finish();
        startActivity(new Intent(getBaseContext(), LoginChoice.class));
    }

    private void userCheckCal(boolean callrequired) {
        final UserPreference userPreference = new UserPreference(RestaurantHomeScreen.this);
        if (userPreference.getAccountStatus().equals("1")) {
            Snackbar.make(findViewById(android.R.id.content), "Account not verified contact developer at divyanfun@gmail.com", Snackbar.LENGTH_INDEFINITE).show();
        } else if (callrequired) {
            String resid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            NetworkService networkService = RetrofitInstanceClient.getRetrofit().create(NetworkService.class);
            userCheckCall = networkService.userCheck(resid);
            userCheckCall.enqueue(new Callback<SignUpCall>() {
                @Override
                public void onResponse(Call<SignUpCall> call, Response<SignUpCall> response) {
                    Log.e("tag", "" + response.body().getStatus());
                    UserPreference nob = new UserPreference(RestaurantHomeScreen.this);
                    if (response.body().getError().equals("1")) {
                        nob.setAccountStatus(RestaurantHomeScreen.this, response.body().getError());
                    Snackbar.make(findViewById(android.R.id.content), "Account not verified contact developer at divyanfun@gmail.com", Snackbar.LENGTH_INDEFINITE).show();
                    } else {
                        nob.setAccountStatus(RestaurantHomeScreen.this, response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<SignUpCall> call, Throwable t) {
                    Log.e("tag", "" + t);
                }
            });
        }
    }

    Snackbar snackbar;

    void noInternet() {
        if (snackbar == null) {
            snackbar = Snackbar.make(findViewById(android.R.id.content), "Seems like you are offline", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkConnection();
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else {
            snackbar.show();
        }
    }

    void checkConnection() {
        if (!isNetworkConnected()) {
            noInternet();
        } else {
            if (snackbar != null && snackbar.isShown())
                snackbar.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
        navigationView.setCheckedItem(R.id.nav_home);
        if (nob != null) {
            navdrawer_title.setText(nob.getRestname());
            Glide.with(getApplicationContext()).load(nob.getProfilePic()).placeholder(R.drawable.templogo).error(R.drawable.templogo).into(profilepic);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
